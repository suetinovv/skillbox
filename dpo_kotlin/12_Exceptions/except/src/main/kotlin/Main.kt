fun main(args: Array<String>) {
    val wheel1 = Wheel()
    val wheel2 = Wheel()
    val wheel3 = Wheel()
    val wheel4 = Wheel()

    wheel1.getPressure()
    wheel1.pumpUp(1.0)
    wheel1.getPressure()
    wheel2.pumpUp(-5.4)
    wheel2.pumpUp(10.4)
    wheel2.getPressure()
    wheel3.pumpUp(2.0)
    wheel3.getPressure()
    wheel4.pumpUp(6.0)
    wheel4.getPressure()
}