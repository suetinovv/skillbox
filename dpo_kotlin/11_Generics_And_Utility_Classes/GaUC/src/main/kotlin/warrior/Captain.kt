package warrior

import weapon.Weapons

class Captain : AbstractWarrior(130, 50, Weapons.createTwoPistols()) {
    override val evasion: Int
        get() = 20
}