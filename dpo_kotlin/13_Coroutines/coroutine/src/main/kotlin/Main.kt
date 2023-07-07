import kotlinx.coroutines.*

fun main(args: Array<String>) {
    runBlocking {
        val job3 = launch {
            try {
                withTimeout(3000) {
                    println(Fibonacci.take(1199955))
                }
            } catch (e: TimeoutCancellationException) {
                println("Время вычисления истекло!")
            }
        }

        val job1 = launch {
            try {
                withTimeout(2000) {
                    println(Fibonacci.take(199955))
                }
            } catch (e: TimeoutCancellationException) {
                println("Время вычисления истекло!")
            }
        }

        val job2 = launch {
            try {
                delay(2000)
                println(Fibonacci.take(155))
            } catch (e: TimeoutCancellationException) {
                println("Время вычисления истекло!")
            }
        }

        launch {
            var i = 1
            while (job1.isActive || job2.isActive || job3.isActive) {
                if (i % 70 == 0) {
                    println(".")
                } else {
                    print(".")
                }
                i++
                delay(50)
            }
        }
    }
}