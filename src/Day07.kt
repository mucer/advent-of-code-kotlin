fun main() {
    val hands = readInput("Day07")
        .map {
            val (cards, bidStr) = it.split(" ")
            Hand(cards, bidStr.toInt())
        }

    hands
        .sortedBy { it.score }
        // .onEach { println("${it.cards}: ${it.score.toString(2).padStart(32, '0')} ${it.type}") }
        .mapIndexed { i, h -> (i + 1) * h.bid }
        .sum()
        .println()
}

data class Hand(
    val cards: String,
    val bid: Int,
) {
    val type: HandType by lazy {
        val counts = mutableMapOf<Char, Int>()
        cards.forEach {
            val newCount = counts.computeIfAbsent(it) { 0 } + 1
            counts[it] = newCount
        }

        val jokers = counts.remove('J') ?: 0
        val max = if (counts.isEmpty()) 0 else counts.values.max()

        val twoPairs = counts.filterValues { it == 2 }.size == 2

        if (max + jokers == 5) HandType.FIVE
        else if (max + jokers == 4) HandType.FOUR
        else if (counts.containsValue(3) && counts.containsValue(2) || twoPairs && jokers == 1) HandType.FULL_HOUSE
        else if (max + jokers == 3) HandType.THREE
        else if (twoPairs || counts.containsValue(2) && jokers == 1) HandType.TWO_PAIRS
        else if (max + jokers == 2) HandType.PAIR
        else HandType.HIGH_CARD
    }

    val score: Int by lazy {
        val cardScore = cards
            .reversed()
//            .onEachIndexed { i, c -> println("i=$i, c=$c, o=${CARD_ORDER.indexOf(c).toString(2)}, s=${(CARD_ORDER.indexOf(c) shl i * 4).toString(2)}")}
            .mapIndexed { i, c -> CARD_ORDER.indexOf(c) shl i * 4 }
            .sum()

        // println("cards=$cards, cardScore=${cardScore.toString(2)}), typeScore=${type.score.toString(2)} $type")
        cardScore + type.score
    }
}

const val CARD_ORDER = "J23456789TQKA"

enum class HandType(val score: Int) {
    FIVE(6 shl 20),
    FOUR(5 shl 20),
    FULL_HOUSE(4 shl 20),
    THREE(3 shl 20),
    TWO_PAIRS(2 shl 20),
    PAIR(1 shl 20),
    HIGH_CARD(0)
}