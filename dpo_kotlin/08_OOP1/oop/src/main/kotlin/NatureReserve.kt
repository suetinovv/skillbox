import kotlin.random.Random

class NatureReserve {
    private var listAnimal = mutableListOf<Animal>()
    private var listDescendants = mutableListOf<Animal>()

    init {
        listAnimal.add(Bird("Vorova", 5, 5, 9))
        listAnimal.add(Bird("Vorobei", 7, 2, 7))
        listAnimal.add(Bird("Sinichka", 3, 2, 8))
        listAnimal.add(Bird("Soroka", 6, 4, 3))
        listAnimal.add(Bird("Orel", 15, 10, 15))

        listAnimal.add(Fish("Osetr", 2, 3, 5))
        listAnimal.add(Fish("Karp", 3, 2, 7))
        listAnimal.add(Fish("Vobla", 4, 4, 3))

        listAnimal.add(Dog("Ovcharka", 5, 3, 7))
        listAnimal.add(Dog("Taksa", 7, 2, 4))

        listAnimal.add(Animal("Cat", 3, 1, 6))
        listAnimal.add(Animal("Mouse", 4, 1, 5))
    }

    fun startCycle(n: Int) {
        for (i in 1..n) {
            listAnimal.forEach { randomAction(it) }
            listAnimal.addAll(listDescendants)
            listDescendants.clear()
            listAnimal.removeAll { it.isTooOld }
            println()
            println("Количество оставшихся животных: ${listAnimal.size}")
            println()
            if (listAnimal.size == 0) {
                println("Все животные умерли!")
                break
            }
            Thread.sleep(1000)
        }
    }

    private fun randomAction(animal: Animal) {
        when (Random.nextInt(1, 5)) {
            1 -> animal.eat()
            2 -> animal.move()
            3 -> animal.sleep()
            4 -> listDescendants.add(animal.producesOffspring())
        }
        Thread.sleep(300)
    }
}