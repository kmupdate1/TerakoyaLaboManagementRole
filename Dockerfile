# --- ステージ1: ビルドステージ ---
# KotlinとGradleのビルド環境を持つイメージを使用
FROM gradle:8.14 AS builder

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
FROM eclipse-temurin:21-jre-jammy

# 環境変数でポートを設定（Cloud Runは通常8080ポートを期待します）
ENV PORT 8080
ENV HOST 0.0.0.0

# ビルドステージで作成したJARファイルをコピー
COPY --from=builder /app/presentation/build/libs/service-api-travel.jar app.jar

# コンテナ起動時にアプリケーションを実行
ENTRYPOINT ["java", "-jar", "app.jar"]
