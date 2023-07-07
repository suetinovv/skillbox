abstract class DebitCard : BankCard() {

    override fun replenish(sum: Int) {
        balance += sum
        println("Баланс пополнен на сумму: $sum")
        println()
        getInfoBalance()
    }

    override fun getInfoBalance() {
        println("Текущий баланс: $balance")
        println()
    }

    override fun toPay(sum: Int): Boolean {
        println("Оплата $sum")
        return if (sum <= balance) {
            balance -= sum
            println()
            getInfoBalance()
            true
        } else {
            println("Недостаточно средств!")
            println()
            false
        }
    }

    override fun getInfoAvailableFunds() {
        println(
            """Информация по счету:
        | Собственные средства: $balance
        | Бонусы: $bonus""".trimMargin()
        )
        println()
    }
}