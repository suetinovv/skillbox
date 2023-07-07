package warrior

interface Warrior {
    var isKilled: Boolean
    val evasion: Int

    fun attack(opponent: Warrior)

    fun takeDamage(damage: Int)
}