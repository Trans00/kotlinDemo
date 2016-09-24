import java.math.BigDecimal

operator fun BigDecimal.inc(): BigDecimal{
    return add(BigDecimal.ONE)
}

fun main(args: Array<String>) {
    var big = BigDecimal(100)
    println("Big decimal is ${++big}")
    val list = listOf("0","1","2")
    println("List member is ${list[2]}")
    "1234567890".forEach { println(it) }
}

