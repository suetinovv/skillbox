import java.util.Objects

object Converters {
    val listConverter = listOf<CurrencyConverter>(Dollar(), Euro())

    fun get(currencyCode: String): CurrencyConverter {
        listConverter.forEach {
            if (it.currencyCode == currencyCode.uppercase()) {
                return it
            }
        }
        return object: CurrencyConverter{
            override val currencyCode: String
                get() = TODO("Not yet implemented")

            override fun convertToRub(sum: Double) {
                println("$currencyCode такой валюты нет!")
            }

        }
    }
}