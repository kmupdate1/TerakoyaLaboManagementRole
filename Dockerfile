# --- ステージ1: ビルドステージ ---
# KotlinとGradleのビルド環境を持つイメージを使用
FROM gradle:8.13-jdk-21-and-24 AS builder
# FROM eclipse-temurin:23-jdk AS builder
# FROM eclipse-temurin:21-jdk AS builder

# curl と unzip をインストール
RUN apt-get update && apt-get install -y curl unzip && rm -rf /var/lib/apt/lists/*

# Gradle インストール（zip ダウンロード & 展開）
ENV GRADLE_VERSION=8.13
RUN curl -sSLo gradle.zip https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip \
    && unzip gradle.zip -d /opt \
    && rm gradle.zip
ENV PATH="/opt/gradle-${GRADLE_VERSION}/bin:${PATH}"

# 作業ディレクトリを設定
WORKDIR /app

# Gradleのビルドキャッシュを効率的に利用するため、まずビルドファイルをコピー
# settings.gradle.kts, build.gradle.kts, gradle.properties など
COPY settings.gradle.kts .
COPY build.gradle.kts .
COPY gradle.properties .
COPY gradlew .
COPY gradle/libs.versions.toml gradle/libs.versions.toml
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/
COPY gradle/wrapper/gradle-wrapper.properties gradle/wrapper/
COPY buildSrc/ buildSrc/

# 依存関係をダウンロードしてキャッシュ
# これにより、ソースコードが変更されても依存関係の再ダウンロードが不要になることが多い
RUN gradle dependencies

# ソースコードをコピー
COPY presentation presentation/
COPY application application/
COPY infrastructure infrastructure/
COPY common common/
COPY shared shared/

# Ktorアプリケーションをビルドし、単一のJARファイルを作成
# build.gradle.kts に shadowJar プラグインが設定されていることを前提とします
# 例: id("com.github.johnrengelman.shadow") version "8.1.1"
RUN ./gradlew :presentation:shadowJar

# --- ステージ2: 実行ステージ ---
# より軽量なJREのみのイメージを使用
# FROM eclipse-temurin:21-jre-jammy
FROM eclipse-temurin:21-jdk

# 環境変数でポートを設定（Cloud Runは通常8080ポートを期待します）
ENV PORT 8080
ENV HOST 0.0.0.0
ENV MONGODB_URI mongodb+srv://laxpotcx1562:4XmylUkUkxGvAjyM@terakoyalabo.f9sfd10.mongodb.net/
ENV MONGODB_NAME management_role

# ビルドステージで作成したJARファイルをコピー
COPY --from=builder /app/presentation/build/libs/service-api-travel.jar app.jar

# コンテナ起動時にアプリケーションを実行
ENTRYPOINT ["java", "-jar", "app.jar"]
