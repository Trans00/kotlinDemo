import sun.management.Agent

/**
 * Created by Denis on 24.09.2016.
 */
data class DataClass(val name: String, val age: Int)

fun main(args: Array<String>) {
    val (name, age) = DataClass("Denis", 26)

    println("Hello my name is $name I am $age years old")
}