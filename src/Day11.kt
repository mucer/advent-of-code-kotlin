import kotlin.math.abs

fun main() {
    val input = readInput("Day11")

    val exp = 100L
    val emptyRows = input.withIndex()
            .filter { !it.value.contains('#') }
            .map { it.index }
    val emptyCols = (0 until input[0].length)
            .filter { col -> input.all { row -> row[col] != '#' } }

    calc(input, emptyRows, emptyCols, 2).println()
    calc(input, emptyRows, emptyCols, 1_000_000).println()
}

private fun calc(
        input: List<String>,
        emptyRows: List<Int>,
        emptyCols: List<Int>,
        exp: Long
): Long {
    var rowOffset = 0
    var galaxies = mutableListOf<Galaxy>()
    input.withIndex().forEach { (row, line) ->
        if (emptyRows.contains(row)) rowOffset++
        var colOffset = 0
        for (col in line.indices) {
            if (emptyCols.contains(col)) colOffset++
            if (line[col] == '#')
                galaxies += Galaxy(
                        galaxies.size + 1,
                        col + colOffset * exp - colOffset,
                        row + rowOffset * exp - rowOffset
                )
        }
    }

    var sum = 0L
    for (i in galaxies.indices) {
        for (j in i + 1 until galaxies.size) {
            val g1 = galaxies[i]
            val g2 = galaxies[j]
            sum += abs(g1.x - g2.x) + abs(g1.y - g2.y)
        }
    }
    return sum
}

data class Galaxy(val i: Int, val x: Long, val y: Long)

fun List<Galaxy>.print() {
    val maxX = this.maxBy { it.x }!!.x
    val maxY = this.maxBy { it.y }!!.y

    for (y in 0..maxY) {

        for (x in 0..maxX) {
            val galaxy = this.firstOrNull { it.x == x && it.y == y }
            if (galaxy != null) print('#') else print('.')
        }
        println("")
    }
}