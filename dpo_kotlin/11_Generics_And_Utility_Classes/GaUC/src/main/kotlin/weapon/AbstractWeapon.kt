package weapon

import Stack

abstract class AbstractWeapon(
    private var maxAmmo: Int,
    var fireType: FireType,
) {
    private var magazineAmmo: Stack<Ammo> = Stack()
    var isAmmoMagazine: Boolean = !magazineAmmo.isEmpty()

    abstract fun createAmmo(): Ammo

    fun recharge() {
        magazineAmmo = Stack()
        repeat((1..maxAmmo).count()) {
            magazineAmmo.push(createAmmo())
        }
        isAmmoMagazine = !magazineAmmo.isEmpty()
    }

    fun getAmmo(fireType: FireType): MutableList<Ammo> {
        val resultList = mutableListOf<Ammo>()

        if (!magazineAmmo.isEmpty()) {
            repeat((1..fireType.sizeQueue).count()) {
                magazineAmmo.pop()?.let { it1 -> resultList.add(it1) }
            }
        }
        isAmmoMagazine = !magazineAmmo.isEmpty()
        return resultList
    }
}