package warrior

import weapon.Weapons

class General : AbstractWarrior(150, 70, Weapons.createTomato()) {
    override val evasion: Int
        get() = 30
}