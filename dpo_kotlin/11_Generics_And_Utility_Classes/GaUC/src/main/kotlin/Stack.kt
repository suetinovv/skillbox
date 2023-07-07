import java.util.Stack

class Stack<T> {
    private val stack = Stack<T>()

    fun push(item: T) {
        stack.push(item)
    }

    fun pop(): T? {
        return stack.pop()
    }

    fun isEmpty(): Boolean {
        return stack.empty()
    }
}