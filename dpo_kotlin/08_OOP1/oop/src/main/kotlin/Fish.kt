import kotlin.random.Random

class Fish(nameArg: String, maxAgeArg: Int, weightArg: Int, energyArg: Int) :
    Animal(nameArg, maxAgeArg, weightArg, energyArg) {
    override fun move():Boolean {
        if (super.move()){
            println("Плывет")
            return true
        }
        return false
    }

    override fun producesOffspring(): Fish {
        val descendant = Fish(
            this.name,
            this.maxAge,
            Random.nextInt(1, 5),
            Random.nextInt(1, 10)
        )
        println(
            "Было рождено животное: ${descendant.name}," +
                    " с максимальным возрастом ${descendant.maxAge}," +
                    " весом ${descendant.weight} и " +
                    "энергией ${descendant.energy},"
        )
        return descendant
    }
}