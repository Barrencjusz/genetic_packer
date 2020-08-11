package genetic.api.individual

data class Cell<T>(
    val order: Int,
    val processingOrder: Int,
    val nucleus: T
)