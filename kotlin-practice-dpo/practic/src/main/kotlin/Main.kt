fun main(args: Array<String>) {

    fun createCountNumber(): Int { // функция проверяет, чтобы пользователь ввел положительное число
        var number: String
        do {
            print("Введите положительное целое число: ")
            number = readln()
            if (number.all { it.isDigit() } && number != "") {
                if (number.toInt() > 0) {
                    return number.toInt()
                }
            }
            print("Ошибка ввода! ")
        } while (true)
    }

    fun createListNumbers(count: Int): MutableList<String> { // функция возращает список согласно условию
        val resultList = mutableListOf<String>()
        var numberTel: String
        var counter = 0
        do {
            print("Введите номер телефона: ")
            numberTel = readln()

            // проверка номера, если номер начинается с '+' то цифр должно быть 11, если номер начинается с цифры, то номер состоит от 2 до 11 цифр
            if ((numberTel[0] == '+'
                        && numberTel.length == 12
                        && numberTel.substring(1, numberTel.length).all { it.isDigit() })
                || (numberTel.all { it.isDigit() }
                        && numberTel.length in 2..11)) {
                resultList.add(numberTel)
                counter++
            } else {
                println("Ошибка ввода! Телефон должен начинаться со знака '+' или цифры и содержать от 2 до 11 цифр")
            }
        } while (counter != count)
        return resultList
    }

    val countNumber = createCountNumber()
    val listNumbers = createListNumbers(countNumber)

    listNumbers // выводятся значения начинающие с +7
        .filter { it.startsWith("+7") }
        .forEach { println(it) }

    val setNumbers = listNumbers.toSet()

    println(setNumbers.size) //выводится количество уникальных номеров

    // sumBy устарела, вместо нее sumOf, выводится сумма всех номеров не только уникальных
    println(listNumbers.sumOf { it.length })

    val mutableMapNumbers = mutableMapOf<String,String>()

    // в задании не сказано что нужны уникальные номера начинающие c +7, поэтому тут все номера из общего списка
    setNumbers.forEach{
        print("Введите имя для номера $it: ")
        val name = readln()
        mutableMapNumbers[it] = name
    }

    // в задании не сказано что нужны уникальные номера начинающие c +7, поэтому тут все номера из общего списка
    mutableMapNumbers.forEach { (t, u) ->
        println("Человек: $u. Номер телефона: $t")
    }
}