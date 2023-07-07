class TooLowPressure: Throwable() {
    override val message: String?
        get() = "Эксплуатация невозможна — давление слишком маленькое."
}