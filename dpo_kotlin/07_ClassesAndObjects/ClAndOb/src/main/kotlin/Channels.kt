object Channels {
    val listChanels =
        listOf("Первый канал", "Россия", "НТВ", "ТНТ", "СТС", "ТВЦ", "РенТВ", "МузТВ", "Карусель", "ОРТ", "Дисней")

    fun getRandomChannels(count: Int): List<String> {
        return listChanels.shuffled().subList(0, count)
    }
}