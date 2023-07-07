class IncorrectPressure: Throwable() {
    override val message: String?
        get() = "процедура не удалась."
}