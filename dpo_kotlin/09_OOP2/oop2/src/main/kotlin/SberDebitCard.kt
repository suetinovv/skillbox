class SberDebitCard() : DebitCard() {

    override fun toPay(sum: Int): Boolean { // бонус 1% от всех покупок больше 100 р
        return if (super.toPay(sum)) {
            bonus += sum / 100
            true
        } else {
            false
        }
    }

}