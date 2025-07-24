package jp.terakoyalabo.common.exception.infrastructure

open class CollectionOperationFailedException(message: String?): RuntimeException(message)
class DocumentCreateFailedException(message: String?): CollectionOperationFailedException(message)
class DocumentUpdateFailedException(message: String?): CollectionOperationFailedException(message)
class DocumentDeleteFailedException(message: String?): CollectionOperationFailedException(message)
