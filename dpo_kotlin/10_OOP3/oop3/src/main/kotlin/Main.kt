fun main(args: Array<String>) {
    val euroConverter = Converters.get("EUR")
    val usdConverter = Converters.get("USD")
    val testConverter = Converters.get("BY")

    euroConverter.convertToRub(654.00)
    usdConverter.convertToRub(654.00)
    testConverter.convertToRub(123.00)

}