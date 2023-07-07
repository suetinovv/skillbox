package weapon

object Weapons {

    fun createPistol() = object : AbstractWeapon(10, FireType.FireSingle) {
        override fun createAmmo() = Ammo.SIMPLE
    }

    fun createTwoPistols() = object : AbstractWeapon(20, FireType.FireQueue(2)) {
        override fun createAmmo() = Ammo.SILVER
    }

    fun createTomato() = object : AbstractWeapon(30, FireType.FireQueue(3)) {
        override fun createAmmo() = Ammo.GOLD
    }

    fun createVintovka() = object : AbstractWeapon(5, FireType.FireSingle) {
        override fun createAmmo() = Ammo.SILVER
    }
}