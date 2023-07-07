sealed class BattleState {

    class Progress(private val battle: Battle) : BattleState() {

        override fun toString(): String {
            return """
            Команда 1:
                 | Сумма HP: ${battle.team1.getSumTeamHP()}
                 | Количество живых игроков: ${battle.team1.getSumTeamWarrior()}
            Команда 2:
                 | Сумма HP: ${battle.team2.getSumTeamHP()}
                 | Количество живых игроков: ${battle.team2.getSumTeamWarrior()}
            """.trimIndent()
        }
    }

    object WinOneTeam : BattleState() {
        override fun toString(): String {
            return "Команда 1 Победила!"
        }
    }

    object WinTwoTeam : BattleState() {
        override fun toString(): String {
            return "Команда 2 Победила!"
        }
    }

    object Draw : BattleState()

}
