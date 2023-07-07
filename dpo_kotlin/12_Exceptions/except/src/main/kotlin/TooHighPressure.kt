class TooHighPressure: Throwable() {
    override val message: String?
        get() = "Эксплуатация невозможна — давление превышает нормальное."
}