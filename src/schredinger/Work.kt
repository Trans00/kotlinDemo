package schredinger

fun doWork(seconds: Int) {
    println("Going to work for $seconds seconds on thread ${Thread.currentThread().name}")
    for (i in 1..seconds) {
        println("Working on thread ${Thread.currentThread().name}...")
        Thread.sleep(1000L)
    }
    println("Worked for $seconds seconds on thread ${Thread.currentThread().name}")
}
