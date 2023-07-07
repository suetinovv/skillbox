fun main(args: Array<String>) {
    val tv1 = TV("model1", 56.4, 7)
    val tv2 = TV("model2", 29.3, 9)

    println("Создан телевизор: ${tv1.brand} ${tv1.model} ${tv1.diagonalSize} c ${tv1.numberCh} каналами")

    tv1.power()
    Thread.sleep(500)
    tv1.printChannelList()
    Thread.sleep(1000)
    for (i in 0..10){
        tv1.upChannel()
        Thread.sleep(500)
    }
    tv1.channelSwitching(5)
    Thread.sleep(500)
    tv1.channelSwitching(19)
    Thread.sleep(500)
    for (i in 0..10){
        tv1.downChannel()
        Thread.sleep(500)
    }
    tv1.upVolume(40)
    Thread.sleep(500)
    tv1.downVolume(40)
    Thread.sleep(500)
    tv1.upVolume(11)
    Thread.sleep(500)
    tv1.power()
    Thread.sleep(500)
    tv1.channelSwitching(3)
    Thread.sleep(500)
    tv1.power()
    Thread.sleep(500)
    tv1.upChannel()
    Thread.sleep(500)

    println()
    println("Создан телевизор: ${tv2.brand} ${tv2.model} ${tv2.diagonalSize} c ${tv2.numberCh} каналами")

    tv2.power()
    Thread.sleep(500)
    tv2.printChannelList()
    Thread.sleep(1000)
    for (i in 0..10){
        tv2.upChannel()
        Thread.sleep(500)
    }
    tv2.channelSwitching(15)
    Thread.sleep(500)
    tv2.channelSwitching(9)
    Thread.sleep(500)
    for (i in 0..10){
        tv2.downChannel()
        Thread.sleep(500)
    }
    tv2.upVolume(40)
    Thread.sleep(500)
    tv2.downVolume(40)
    Thread.sleep(500)
    tv2.upVolume(17)
    Thread.sleep(500)
    tv2.power()
    Thread.sleep(500)
    tv2.channelSwitching(5)
    Thread.sleep(500)
    tv2.power()
    Thread.sleep(500)
    tv2.upChannel()
    Thread.sleep(500)
}