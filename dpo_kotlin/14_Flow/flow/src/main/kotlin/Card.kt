class Card(private val namePlayer: String) {

    private var card = createCard()
    var isClosedLine = false
    private val nameCard = "Карточка № ${counter++}"
    private fun createCard(): Array<Array<String>> {
        val resultArray = Array(3) { Array(9) { "  " } }
        var temp: Int
        var counter: Int
        for (i in 0..2) {
            counter = 0
            while (counter < 5) {
                temp = (0..8).random()
                if (resultArray[i][temp] == "  ") {
                    if (i != 2 || resultArray[0][temp] == "  " || resultArray[1][temp] == "  ") {
                        resultArray[i][temp] = getNumber(i, temp)
                        counter++
                    }
                } else {
                    continue
                }
            }
        }
        return resultArray
    }

    private fun getNumber(row: Int, col: Int): String {
        val temp = if (col == 0) {
            ((row * 3 + 1)..((row + 1) * 3)).random()
        } else if (row == 2 && col == 8) {
            (87..90).random()
        } else {
            ((row * 3) + (col * 10)..(col * 10) + ((row + 1) * 3)).random()
        }
        return if (temp < 10) {
            " $temp"
        } else {
            "$temp"
        }
    }

    private fun printCard() {
        println("---------------------------------------------")
        for (i in 0..2) {
            print("|")
            for (j in 0..8) {
                print("${card[i][j]} | ")
            }
            println()
            println("---------------------------------------------")
        }
    }

    fun checkNumber(number: Int) {
        val temp = if (number < 10) {
            " $number"
        } else {
            number.toString()
        }
        for (i in 0..2) {
            for (j in 0..8) {
                if (card[i][j] == temp) {
                    card[i][j] = " X"
                    println("У $namePlayer есть число $number в $nameCard")
                    printCard()
                    checkLine(i)
                }
            }
        }
    }

    private fun checkLine(row: Int) {
        for (i in 0..8) {
            if (!(card[row][i] == " X" || card[row][i] == "  ")) {
                return
            }
        }
        isClosedLine = true
    }

    companion object {
        var counter = 1
    }
}