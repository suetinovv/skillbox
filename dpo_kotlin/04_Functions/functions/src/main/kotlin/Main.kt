fun main(args: Array<String>) {
    val encodedString = "F2p)v\"y233{0->c}ttelciFc"

    fun shift(src: String, tr: (char: Char) -> Char): String {
        return src.map(tr).joinToString("")
    }

    fun firstPartLine(line: String): String {
        var resultLine = line.substring(0, line.length / 2)
        resultLine = shift(resultLine) { it + 1 }
        resultLine = resultLine.replace("5", "s")
        resultLine = resultLine.replace("4", "u")
        resultLine = shift(resultLine) { it - 3 }
        resultLine = resultLine.replace("0", "o")
        return resultLine
    }

    fun secondPartLine(line: String): String {
        var resultLine = line.substring(line.length / 2).reversed()
        resultLine = shift(resultLine) { it - 4 }
        resultLine = resultLine.replace("_", " ")
        return resultLine
    }

    fun decoder(line: String) {
        val decodedString = firstPartLine(line) + secondPartLine(line)
        println(decodedString)
    }

    decoder(encodedString)
}