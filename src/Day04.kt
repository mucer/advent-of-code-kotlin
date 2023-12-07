import kotlin.math.pow

fun main() {
    val lines = readInput("Day04")

    val cards = lines.map(::readCard)

    cards
        .map(Card::points)
        .sum()
        .println()

    cards
        .map { evalCard(cards, it) }
        .sum()
        .println()
}

val SPACES = Regex("\\s+")
fun readCard(line: String): Card {
    val (idStr, numberStr) = line.split(":")
    val id = idStr.substring(5).trim().toInt()
    val (winningStr, ownStr) = numberStr.split("|")
    val winning = winningStr.trim().split(SPACES).map(String::toInt)
    val own = ownStr.trim().split(SPACES).map(String::toInt)
    return Card(id, winning, own)
}

fun evalCard(cards: List<Card>, card: Card): Int {
    val matches = card.matches()
    var total: Int = 1
    for (i in 0..<matches) {
        total += evalCard(cards, cards[card.id + i])
    }
    return total
}

data class Card(
    val id: Int,
    val winning: List<Int>,
    val own: List<Int>
) {
    fun matches(): Int = own.filter { winning.contains(it) }.size
    fun points(): Int {
        val num = matches()
        return if (num == 0) return 0
        else 2.0.pow(num.toDouble() - 1).toInt()
    }
}