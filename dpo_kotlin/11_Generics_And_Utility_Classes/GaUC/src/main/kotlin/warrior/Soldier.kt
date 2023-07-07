package warrior

import weapon.Weapons

class Soldier : AbstractWarrior(100, 30, Weapons.createPistol()) {
    override val evasion: Int
        get() = 10
}