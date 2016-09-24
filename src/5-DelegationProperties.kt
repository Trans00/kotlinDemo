import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class PropertyClass(){
    var field by Delegates.notNull<String>()
//    var field by MyPropertyDelegate<String>()
}


fun main(args: Array<String>) {
    val prop = PropertyClass()
    prop.field = "one"
    println(prop.field)

    prop.field = "two"
    println(prop.field)
}




























class MyPropertyDelegate<T: Any>(){
    private var stored: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return stored
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        if(stored == null){
            stored = value
        } else {
            throw IllegalStateException("You can't reassign property ${property.name}")
        }
    }

}