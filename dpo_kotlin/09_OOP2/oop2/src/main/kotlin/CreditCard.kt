abstract class CreditCard(val kreditLimit: Int) : BankCard() {

    protected var kreditBalance = kreditLimit

    override fun replenish(sum: Int) {
        if (kreditBalance == kreditLimit) {
            balance += sum
        } else {
            if (kreditLimit - kreditBalance >= sum) {
                kreditBalance += sum
            } else {
                balance += sum - (kreditLimit - kreditBalance)
                kreditBalance = kreditLimit
            }
        }
        println("Баланс пополнен на сумму: $sum")
        println()
        getInfoBalance()
    }

    override fun getInfoBalance() {
        println(
            """Текущий баланс: ${balance + kreditBalance}
        | Из них:
        | Собственные средства: $balance
        | Кредитные средства: $kreditBalance""".trimMargin()
        )
        println()
    }

    override fun toPay(sum: Int): Boolean {
        println("Оплата $sum")
        return if (sum <= balance + kreditBalance) {
            if (sum <= balance) {
                balance -= sum
            } else {
                kreditBalance -= sum - balance
                balance = 0
            }
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
        | Кредитные средства: $kreditBalance
        | Кредитный лимит: $kreditLimit
        | Бонусы: $bonus""".trimMargin()
        )
        println()
    }
}