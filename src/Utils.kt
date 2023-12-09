import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

fun List<Long>.product() = reduce { acc, l -> acc * l }

fun List<Pair<Long, Long>>.sum() =
    reduce { acc, pair -> acc.first + pair.first to acc.second + pair.second }

fun List<Long>.lcm(): Long = reduce { a, b -> lcm(a, b) }

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)
val SPACES = Regex("\\s+")