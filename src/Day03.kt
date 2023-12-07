fun main() {
    val lines = readInput("Day03")
    val parts = mutableListOf<Part>()
    for (i in lines.indices) {
        parts += readParts(i,
            lines.getOrNull(i - 1),
            lines[i],
            lines.getOrNull(i + 1)
        )
    }

    parts
        .map(Part::number)
        .sum()
        .println()

    val gears = parts
        .filter { it.symbol == "*" }
        .groupBy { it.index }
        .filter { it.value.size == 2 }


    gears
        .map { it.value.first().number * it.value.last().number }
        .sum()
        .println()
}


val DIGITS = Regex("\\d+")
val SYMBOL = Regex("[^\\d.]")
fun readParts(lineIndex: Int, prev: String?, line: String, next: String?): List<Part> {
    var digits = DIGITS.find(line)
    val parts = mutableListOf<Part>()
    while (digits != null) {
        val texts = listOf(
            prev.sub(digits.range),
            line.sub(digits.range),
            next.sub(digits.range)
        )

        for (i in texts.indices) {
            val symbol = SYMBOL.find(texts[i])
            if (symbol != null) {
                val offset = if (digits.range.first == 0) 0 else -1

                parts += Part(
                    digits.value.toInt(),
                    symbol.value,
                    (lineIndex - 1 + i) * line.length + offset + digits.range.first + symbol.range.first)
            }
        }


        digits = digits.next()
    }
    return parts.toList()
}

fun String?.sub(range: IntRange): String {
    if (this == null) return ""

    val first = (range.first - 1).coerceAtLeast(0)
    val last = (range.last + 2).coerceAtMost(length)

    return substring(first, last)
}

data class Part(
    val number: Int,
    val symbol: String,
    val index: Int,
)

