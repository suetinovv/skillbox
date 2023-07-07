package weapon

sealed class FireType(val sizeQueue: Int) {

    object FireSingle : FireType(1)

    data class FireQueue(val sizeQueueArg: Int) : FireType(sizeQueueArg)
}
