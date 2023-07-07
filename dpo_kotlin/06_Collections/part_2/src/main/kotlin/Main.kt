fun main(args: Array<String>) {

    fun createDB(): HashMap<String, String> { // возвращает созданнаю hashMap из 3-х пар
        val resultHashMap = hashMapOf<String, String>()
        var login: String
        var pass: String
        var count = 1

        println("Подготовка данных начата:")

        do {
            while (true) {
                print("Заполните логин для $count пользователя: ")
                login = readln()
                if (login.length < 3) {
                    print("Логин должен содержать не менее 3 знаков! ")
                    continue
                }
                if (resultHashMap.keys.contains(login)) {
                    print("Логин уже занят! ")
                    continue
                }
                break
            }

            while (true) {
                print("Заполните пароль для $count пользователя: ")
                pass = readln()
                if (pass.length < 6) {
                    print("Пароль должен содержать не менее 6 знаков")
                    continue
                }
                break
            }

            resultHashMap.put(login, pass)
            count++
        } while (count < 4)

        println("Подготовка данных закончена!")

        return resultHashMap
    }

    fun authorization(dateBase: HashMap<String, String>) {
        var login: String
        var pass: String

        println("Авторизация пользователя:")

        do {
            while (true) {
                print("Введите логин: ")
                login = readln()
                if (login.length < 3) {
                    print("Логин должен содержать не менее 3 знаков! ")
                    continue
                }
                if (!dateBase.keys.contains(login)) {
                    print("Такого логина не существует! ")
                    continue
                }
                break
            }

            while (true) {
                print("Введите пароль: ")
                pass = readln()
                if (pass.length < 6) {
                    print("Пароль должен содержать не менее 6 знаков! ")
                    continue
                } else {
                    break
                }

            }
            if (!dateBase.getValue(login).equals(pass)) {
                print("Указан неверный логин или пароль! ")
                continue
            }
            println("Добро пожаловать $login!")
            break
        } while (true)
    }

    val db = createDB()
    println(db)
    authorization(db)

}