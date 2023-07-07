package warrior

import weapon.AbstractWeapon
import chance

abstract class AbstractWarrior(
    MaxHP: Int,
    private val accuracy: Int,
    private val weapon: AbstractWeapon,
) : Warrior {

    var levelHP = MaxHP

    override var isKilled: Boolean
        get() = levelHP <= 0
        set(value) {}

    override fun attack(opponent: Warrior) {
        if (!weapon.isAmmoMagazine) {
            weapon.recharge()
            return
        } else {
            var tempDamage = 0
            weapon.getAmmo(weapon.fireType).forEach {
                if (accuracy.chance()) {
                    if (!opponent.evasion.chance()) {
                        tempDamage += it.getDamage()
                    }
                }
            }
            opponent.takeDamage(tempDamage)
        }
    }

    override fun takeDamage(damage: Int) {
        if (levelHP <= damage) {
            levelHP = 0
            isKilled = true
        } else {
            levelHP -= damage
        }
    }

}