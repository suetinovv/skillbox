import kotlin.random.Random

fun main(args: Array<String>) {
    print("Введите количество игроков: ")
    val numberUsers = readln().toInt()
    val battle = Battle(numberUsers)
    while (!battle.isBattleCompleted) {
        battle.iterationBattle()
        println(battle.getStateBattle().toString())
        println("+++++++++++++++++++++++++++++++++++++++++++++++++")
        Thread.sleep(1000)
    }
}

fun Int.chance(): Boolean {
    return Random.nextInt(0, 100) < this
}