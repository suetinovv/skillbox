import warrior.*
import kotlin.random.Random

class Team(sizeTeam: Int) {
    var listWarrior = mutableListOf<AbstractWarrior>()

    init {
        repeat((1..sizeTeam).count()) {
            listWarrior.add(getRandomWarrior())
        }
    }

    private fun getRandomWarrior(): AbstractWarrior {
        val temp = Random.nextInt(0, 100)
        return if (temp < 10)
            General()
        else if (temp < 40)
            Captain()
        else
            Soldier()
    }

    fun getSumTeamHP(): Int {
        return listWarrior.sumOf { it.levelHP }
    }

    fun getSumTeamWarrior(): Int {
        var result = 0
        listWarrior.forEach {
            if (!it.isKilled)
                result++
        }
        return result
    }

}