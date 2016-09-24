package schredinger

fun doWork(seconds: Int) {
    println("Going to work for $seconds seconds on thread ${Thread.currentThread().name}")
    (1..seconds).forEach {
        println("Working on thread ${Thread.currentThread().name}...")
        Thread.sleep(1000L)
    }
    println("Worked for $seconds seconds on thread ${Thread.currentThread().name}")
}
