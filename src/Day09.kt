fun main() {
    val lines = readInput("Day09")
        .map { it.split(" ").map(String::toInt) }

    lines
        .map(::process)
//        .onEach(::println)
        .sum()
        .println()
}

fun process(numbers: List<Int>): Pair<Long, Long> {
    val row = numbers.toMutableList()
    var part1 = 0L
    val part2 = mutableListOf<Int>()

    while (row.any { it != 0 }) {
        part1 += row.last()
        part2 += row.first()

        for (i in 0..row.size - 2) {
            row[i] = row[i + 1] - row[i]
        }
        row.removeLast()
    }

    val res2 = part2.reduceRight { i, acc -> i - acc }

    return part1 to res2.toLong()
}