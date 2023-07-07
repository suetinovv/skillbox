class Euro : CurrencyConverter {
    override val currencyCode: String
        get() = "EUR"

    override fun convertToRub(sum: Double) {
        println("$sum RUB = ${sum/63.53} $currencyCode")
    }
}