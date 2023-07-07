fun main(args: Array<String>) {
    val firstName = "Vladimir"
    val lastName = "Suetinov"
    var height = 1.40
    var weight: Float = 44.5f
    var isChild = height < 1.5 || weight < 40
    var info = "$firstName $lastName $height $weight $isChild"
    println(info)
    height = 1.84
    weight = 74.5f
    isChild = height < 1.5 || weight < 40
    info = "$firstName $lastName $height $weight $isChild"
    println(info)
}