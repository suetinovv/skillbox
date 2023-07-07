import kotlinx.coroutines.*

class Player(cards: Int) {
    private var listCards = mutableListOf<Card>()
    var isWin = false
    val numberPlayer = countName++
    val scope = CoroutineScope(Game.job + Dispatchers.Default)

    init {
        repeat(cards) {
            listCards.add(Card("Игрок № $numberPlayer"))
        }
    }

    private fun checkCards() {
        listCards.forEach {
            if (it.isClosedLine) {
                isWin = true
            }
        }
    }

    fun checkNumber(number: Int) {
        listCards.forEach {
            it.checkNumber(number)
            checkCards()
        }
    }

    companion object {
        private var countName = 1
    }


}
