import kotlin.random.Random

fun main(args: Array<String>) {
    fun checkNumber(): Int {
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

    val n = checkNumber()

    val listNumbers = List(n) { Random.nextInt(-5, 5) }
    println(listNumbers)

    val mutListNumbers = listNumbers.toMutableList()

    mutListNumbers.forEachIndexed { index, item ->
        if (item % 2 == 0) {
            mutListNumbers[index] = item * 10
        }
    }

    println(mutListNumbers.sumOf { it })

    val sortList = mutListNumbers.filter { it > 0 }
    println(sortList)
}