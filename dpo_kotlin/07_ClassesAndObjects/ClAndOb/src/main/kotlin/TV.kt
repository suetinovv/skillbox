class TV(val model: String, val diagonalSize: Double, numCh: Int) {
    val brand: String = "Samsung"
    private var volume = 10
    val numberCh = minOf(Channels.listChanels.size, numCh)
    var isPowerOn = false
        private set

    private var channel = 1
        private set

    private val listChanel = Channels.getRandomChannels(numberCh)

    fun power() {
        isPowerOn = !isPowerOn
        println(if (isPowerOn) "Телевизор включен" else "Телевизор выключен")
    }

    fun upVolume(up: Int) {
        if (isPowerOn) {
            volume = (minOf(volume + up, maxVolume))
            println("Громкость $volume из $maxVolume")
        }
    }

    fun downVolume(down: Int) {
        if (isPowerOn){
            volume = (maxOf(volume - down, 0))
            println("Громкость $volume из $maxVolume")
        }
    }

    fun channelSwitching(ch: Int) {
        if (!isPowerOn) power()
        if (ch > 0 && ch <= numberCh){
            channel = ch
            println("Канал: $channel - ${listChanel.get(channel - 1)}")
        }
    }

    fun upChannel() {
        if (!isPowerOn) power()
        else {
            channel++
            if (channel > numberCh) channel = 1
        }
        println("Канал: $channel - ${listChanel.get(channel - 1)}")
    }

    fun downChannel() {
        if (!isPowerOn) power()
        else {
            channel--
            if (channel == 0) channel = numberCh
        }

        println("Канал: $channel - ${listChanel.get(channel - 1)}")
    }

    fun printChannelList() {
        if (isPowerOn){
            println("Список каналов: ")
            listChanel.forEach { println("${listChanel.indexOf(it) + 1} - ${it}") }
        }
    }

    companion object {
        private val maxVolume = 30
    }
}