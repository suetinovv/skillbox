abstract class BankCard() {

    protected var balance: Int = 0
    protected var bonus: Int = 0

    abstract fun replenish(sum: Int)

    abstract fun toPay(sum: Int): Boolean

    abstract fun getInfoBalance()

    abstract fun getInfoAvailableFunds()

}