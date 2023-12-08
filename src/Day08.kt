import java.lang.RuntimeException

fun Pair<String, String>.lr(i: Char) = if (i == 'L') first else second

fun List<Long>.lcm(): Long = reduce { a, b -> lcm(a, b) }

fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)

fun lcm(a: Long, b: Long): Long = a / gcd(a, b) * b

val NETWORK = Regex("(\\w+) = \\((\\w+), (\\w+)\\)")

fun main() {
    val lines = readInput("Day08")
    val instructions = lines[0]

    val network = mutableMapOf<String, Pair<String, String>>()
    for (i in 2 until lines.size) {
        val match = NETWORK.matchEntire(lines[i]) ?: throw RuntimeException("Invalid node ${lines[i]}")
        network[match.groupValues[1]] = match.groupValues[2] to match.groupValues[3]
    }

    // part 1
    var node = "AAA"
    var steps = 0
    while (node != "ZZZ") {
        val instruction = instructions[steps % instructions.length]
        node = network[node]!!.lr(instruction)
        steps++
    }
    println(steps)

    // part 2
    network.keys
        .filter { it.endsWith("A") }
        .map { getSteps(instructions, network, it) }
        .lcm()
        .println()
}

fun getSteps(instructions: String, network: Map<String, Pair<String, String>>, startNode: String): Long {
    var node = startNode

    var steps = 0L
    while (!node.endsWith("Z")) {
        val instruction = instructions[(steps % instructions.length).toInt()]
        node = network[node]!!.lr(instruction)
        steps++
    }
    return steps
}
