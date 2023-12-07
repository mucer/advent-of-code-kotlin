import java.lang.RuntimeException

fun main() {

    fun part1(input: List<String>): Int = input
            .map { "${it.find(Char::isDigit)}${it.findLast(Char::isDigit)}" }
            .map(String::toInt)
            .sum()

    fun getNumber(text: String, last: Boolean): Int {
        val find =
                if (last) text.findLastAnyOf(words)
                else text.findAnyOf(words)
        if (find == null) throw RuntimeException("No digit in text '$text' found!")
        return (words.indexOf(find.second) / 2) + 1
    }

    fun part2(input: List<String>): Int = input
            .map { "${getNumber(it, false)}${getNumber(it, true)}" }
            .map(String::toInt)
            .sum()

    // test if impl
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}

val words = listOf(
        "1", "one",
        "2", "two",
        "3", "three",
        "4", "four",
        "5", "five",
        "6", "six",
        "7", "seven",
        "8", "eight",
        "9", "nine")