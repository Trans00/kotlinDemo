/**
 * Created by Denis on 24.09.2016.
 */

fun main(args: Array<String>) {
    val notNull : String? = "notNull"

//    notNull.length
    if (notNull != null) {
        println("length is ${notNull.length}")
    }

    val string : Any = "String"

//    string.length
    if(string is String){
        println("It is String with length ${string.length}")
    }
}
