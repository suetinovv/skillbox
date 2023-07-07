class Dollar : CurrencyConverter {
    override val currencyCode: String
        get() = "USD"

    override fun convertToRub(sum: Double) {
        println("$sum RUB = ${sum/61.03} $currencyCode")
    }

}