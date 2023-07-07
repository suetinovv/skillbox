class SberCreditCard(kreditLimit: Int) : CreditCard(kreditLimit) {

    override fun toPay(sum: Int): Boolean { // бонус 3% с покупок более 3000
        return if (super.toPay(sum)) {
            if (sum >= 3000) bonus += sum / 100 * 3
            true
        } else {
            false
        }
    }

    override fun replenish(sum: Int) { // бонус 1 процент от пополнения
        super.replenish(sum)
        bonus += sum / 100
    }

}