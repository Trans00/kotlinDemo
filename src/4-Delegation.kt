
class Delegate(val appendable: Appendable) : Appendable by appendable{
    override fun toString(): String {
        return appendable.toString()
    }
}

fun main(args: Array<String>) {
    val result = Delegate(StringBuilder()).append("Hello again!").toString()
    println(result)
}

