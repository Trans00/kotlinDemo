
fun Thread.daemon(daemon: Boolean): Thread {
    isDaemon = daemon
    return this
}

fun Thread.name(name: String) : Thread {
    this.name = name
    return this
}

fun main(args: Array<String>) {
    Thread({ println("Hello")}).name("MyThread").daemon(false).start()
}
