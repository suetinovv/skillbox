import warrior.AbstractWarrior

class Battle(private val sizeTeam: Int) {
    val team1: Team = Team(sizeTeam)
    val team2: Team = Team(sizeTeam)
    var isBattleCompleted: Boolean = false

    fun getStateBattle(): BattleState {
        return if (team1.getSumTeamWarrior() > 0 && team2.getSumTeamWarrior() > 0) {
            BattleState.Progress(this)
        } else {
            isBattleCompleted = true
            if (team2.getSumTeamWarrior() == 0) {
                BattleState.WinOneTeam
            } else {
                BattleState.WinTwoTeam
            }
        }
    }

    fun iterationBattle() {
        team1.listWarrior.shuffle()
        team2.listWarrior.shuffle()
        (0 until sizeTeam).forEach {
            if (!team1.listWarrior[it].isKilled) {
                getOpponent(team2)?.let { it1 ->
                    team1.listWarrior[it].attack(it1)
                    println("Игрок ${it + 1} из команды 1, аттаковал игрока ${team2.listWarrior.indexOf(it1) + 1} из команды 2")
                }
            }
            if (!team2.listWarrior[it].isKilled) {
                getOpponent(team1)?.let { it1 ->
                    team2.listWarrior[it].attack(it1)
                    println("Игрок ${it + 1} из команды 2, аттаковал игрока ${team1.listWarrior.indexOf(it1) + 1} из команды 1")
                }
            }
        }
    }

    private fun getOpponent(team: Team): AbstractWarrior? {
        if (team.getSumTeamWarrior() == 0) {
            return null
        } else {
            while (true) {
                val i = (0 until team.listWarrior.size).random()
                if (!team.listWarrior[i].isKilled)
                    return team.listWarrior[i]
            }
        }
    }
}