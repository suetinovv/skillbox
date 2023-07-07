import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow

class Game(private val players: Int, private val cards: Int) {
    private var listPlayers = mutableListOf<Player>()
    fun createGame() {
        repeat(players) {
            listPlayers.add(Player(cards))
        }
    }

    fun startGame() {
        runBlocking {
            launch {
                flow1.collect {
                    if (job.isActive) {
                        println(it)
                        flow.emit(it)
                        delay(((players + 3) * 100).toLong())
                    }
                }
            }
            launch {
                listPlayers.forEach { player ->
                    player.scope.launch {
                        flow.collect {
                            delay((player.numberPlayer * 100).toLong())
                            player.checkNumber(it)
                            if (player.isWin) {
                                println("Игрок № ${player.numberPlayer} победил")
                                job.cancel()
                            }
                        }
                    }
                }
            }
        }
    }


    companion object {
        val job = Job()
        val flow1 = (1..90).shuffled().asFlow()
        val flow = MutableSharedFlow<Int>()
    }
}
