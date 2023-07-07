package weapon

import chance

enum class Ammo(
    private val damage: Int,
    private val chanceCriticalDamage: Int,
    private val coefficientCriticalDamage: Int,
) {

    SIMPLE(10, 10, 2),
    SILVER(20, 20, 3),
    GOLD(30, 25, 5);


    fun getDamage(): Int {
        return if (chanceCriticalDamage.chance())
            damage * coefficientCriticalDamage
        else damage
    }

}