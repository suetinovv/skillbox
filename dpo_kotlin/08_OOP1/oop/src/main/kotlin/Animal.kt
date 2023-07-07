import kotlin.random.Random

open class Animal(
    protected val name: String,
    protected val maxAge: Int,
    protected var weight: Int,
    protected var energy: Int,
) {
    private var currentAge = 0
    var isTooOld = currentAge >= maxAge
        private set

    fun sleep() {
        energy += 5
        if (!isTooOld) {
            currentAge++
            isTooOld = currentAge >= maxAge
        }
        println("${name} спит")
    }

    fun eat() {
        energy += 3
        weight++
        tryIncrementAge()
        println("${name} ест")
    }

    open fun move():Boolean {
        if (energy >= 5) {
            energy -= 5
            weight++
            tryIncrementAge()
            println("${name} передвигается")
            return true
        }
        return false
    }

    private fun tryIncrementAge() {
        if (Random.nextBoolean() && !isTooOld) {
            currentAge++
            isTooOld = currentAge >= maxAge
        }
    }

    open fun producesOffspring():Animal {
        val descendant = Animal(
            this.name,
            this.maxAge,
            Random.nextInt(1, 6),
            Random.nextInt(1, 11)
        )
        println("Было рождено животное: ${descendant.name}," +
                " с максимальным возрастом ${descendant.maxAge}," +
                " весом ${descendant.weight} и " +
                "энергией ${descendant.energy},")
        return descendant
    }

}