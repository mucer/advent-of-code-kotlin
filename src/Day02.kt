import java.lang.RuntimeException

fun main() {
    val max = Colors(12, 13, 14)

    val games = readGames(readInput("Day02"))

    // part 1
    games
        .filter { game -> game.draws.all { it.red <= max.red && it.green <= max.green && it.blue <= max.blue } }
        .map(Game::id)
        .sum()
        .println()

    // part 2
    games
        .map { calcPower(it.draws) }
        .sum()
        .println()
}


val COLOR_REGEX = Regex("(\\d+) (\\w+)")

fun readGames(strs: List<String>): List<Game> =
    strs.map(::readGame)

fun readGame(str: String): Game {
    val (gameStr, drawsStr) = str.split(":", limit = 2)
    val gameId = gameStr.substring(5).toInt()
    val draws = drawsStr.split(";").map(::readDraw)
    return Game(gameId, draws)
}

fun readDraw(str: String): Colors {
    var match = COLOR_REGEX.find(str)
    val colors = Colors()
    while (match != null) {
        val count = match.groupValues[1].toInt()
        when (match.groupValues[2]) {
            "red" -> colors.red += count
            "green" -> colors.green += count
            "blue" -> colors.blue += count
            else -> throw RuntimeException("No color for ${match.groupValues[2]} found")
        }
        match = match.next()
    }

    return colors
}

fun calcPower(colors: List<Colors>): Int {
    val max = colors.reduce { a, b -> a.coerceAtLeast(b) }
    return max.red * max.green * max.blue
}

data class Colors(
    var red: Int = 0,
    var green: Int = 0,
    var blue: Int = 0,

    ) {
    fun coerceAtLeast(o: Colors) = Colors(
        red.coerceAtLeast(o.red),
        green.coerceAtLeast(o.green),
        blue.coerceAtLeast(o.blue)
    )
}

data class Game(
    val id: Int,
    val draws: List<Colors>,
)