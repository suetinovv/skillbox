fun main(args: Array<String>) {
    print("Введите количество игроков: ")
    val players = readln().toInt()
    print("Введите количество карточек: ")
    val cards = readln().toInt()
    val game = Game(players, cards)
    game.createGame()
    game.startGame()
}