interface CurrencyConverter {
    val currencyCode: String

    fun convertToRub(sum: Double)
}