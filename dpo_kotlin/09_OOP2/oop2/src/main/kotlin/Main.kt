fun main(args: Array<String>) {
    val sberDebitCard = SberDebitCard()
    val sberCreditCard = SberCreditCard(10000)

    fun test(card: BankCard) {
        card.getInfoBalance()
        Thread.sleep(300)
        card.getInfoAvailableFunds()
        Thread.sleep(300)
        card.replenish(10000)
        Thread.sleep(300)
        (1..8).forEach { i ->
            card.toPay(i * 1000)
            Thread.sleep(300)
        }
        card.getInfoAvailableFunds()
        Thread.sleep(300)
        (1..5).forEach { i ->
            card.replenish(i * 1000)
            Thread.sleep(300)
        }
        card.getInfoAvailableFunds()
    }

    test(sberDebitCard)
    println("=============================")
    test(sberCreditCard)
}