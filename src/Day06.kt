import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

fun main() {
    val lines = readInput("Day06")
    val times = lines[0].substring(10).trim().split(SPACES).map(String::toDouble)
    val distances = lines[1].substring(10).trim().split(SPACES).map(String::toDouble)

    var total = 1L
    for (i in times.indices) {
        val time = times[i]
        val distance = distances[i]

        total *= calcRace(i, time, distance)
    }

    println(total)

    val totalTime = lines[0].substring(10).replace(SPACES, "").toDouble()
    val totalDistance = lines[1].substring(10).replace(SPACES, "").toDouble()
    println(calcRace(99, totalTime, totalDistance))
}

fun calcRace(num: Int, time: Double, distance: Double): Long {
    val b = sqrt(time.pow(2) - 4 * (distance + 0.1)) / 2
    val min = ceil(time / 2 - b).toLong()
    val max = floor(time / 2 + b).toLong()
    val possible = max - min + 1

    println("Race $num: min=$min, max=$max, possible=$possible")
    return possible
}