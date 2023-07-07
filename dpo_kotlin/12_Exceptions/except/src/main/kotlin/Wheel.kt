class Wheel(private var currentPressure: Double = 0.0) {

    fun pumpUp(pressure: Double) {
        try {
            print("При накачке $pressure атмосфер ")
            if (pressure !in 0.0..10.0) {
                throw IncorrectPressure()
            } else {
                currentPressure = pressure
                print("процедура удалась. ")
                if (pressure > 2.5) {
                    throw TooHighPressure()
                } else if (pressure < 1.6) {
                    throw TooLowPressure()
                } else {
                    println("Эксплуатация возможна")
                }
            }
        } catch (e: Throwable) { // сделал двумя вариантами
            println(e.message)
        }
    }

    fun getPressure() {
        try {
            if (currentPressure < 1.6) {
                throw TooLowPressure()
            } else if (currentPressure > 2.5) {
                throw TooHighPressure()
            } else {
                println("Давление $currentPressure")
            }
        } catch (e: TooLowPressure) { // сделал двумя вариантами
            println(e.message)
        } catch (e: TooHighPressure){
            println(e.message)
        }

    }
}