import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.yield
import java.math.BigInteger

object Fibonacci {
    suspend fun take(n: Int): BigInteger {
        var resultFibonacciWhile = BigInteger.ONE
        var oneNumW = BigInteger.ZERO
        var twoNumW = BigInteger.ONE
        var i = 2
        while (i <= n) {
            yield()
            resultFibonacciWhile = oneNumW + twoNumW
            oneNumW = twoNumW
            twoNumW = resultFibonacciWhile
            i++
        }
        return resultFibonacciWhile
    }
}