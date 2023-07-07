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

    tailrec fun fibonacci(n: Int, zero: Long = 0, one: Long = 1): Long {
        return when (n) {
            0 -> zero
            1 -> one
            else -> fibonacci(n - 1, one, zero + one)
        }
    }



    val n: Int = checkNumber() // если ввести больше 139 произодет целочисленное переполнение
    // Решение с помощью цикла for
    var resultFibonacciFor = 1L
    var oneNumF = 0L
    var twoNumF = 1L

    for (i in 2..n){
        resultFibonacciFor = oneNumF + twoNumF
        oneNumF = twoNumF
        twoNumF = resultFibonacciFor
    }

    println("Результат последовательности с помошью цикла for: $resultFibonacciFor")

    // Решение с помощью цикла while
    var resultFibonacciWhile = 1L
    var oneNumW = 0L
    var twoNumW = 1L
    var i = 2
    while (i <= n){
        resultFibonacciWhile = oneNumW + twoNumW
        oneNumW = twoNumW
        twoNumW = resultFibonacciWhile
        i++
    }
    println("Результат последовательности с помошью цикла while: $resultFibonacciWhile")

    // Решение с помощью рекурсии
    println("Результат последовательности с помошью рекурсии: ${fibonacci(n)}")
}