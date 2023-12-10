fun main() {
    val maze = Maze(readInput("Day10"))

    val start = maze.posOf('S')
    var pos = if (start.up?.down == start) start.up!!
    else if (start.down?.up == start) start.down!!
    else if (start.right?.left == start) start.right!!
    else start.left!!

    val path = mutableListOf(start, pos)
    while (pos != start) {
//        println(" -> $pos")
        pos = pos.next(path[path.size - 2])
        path += pos
    }
    val count = maze.countInner(path)
    println(path.size)
    println(path.size / 2)
    println(count)
}

class Maze(private val lines: List<String>) {
    fun charAt(pos: Pos) = lines[pos.y][pos.x]
    fun posOf(c: Char) = lines
        .withIndex()
        .first { it.value.contains(c) }
        .let { Pos(it.value.indexOf(c), it.index) }

    fun countInner(path: List<Pos>): Int {
        val sb = StringBuilder()
        var inner: Boolean = false
        var count = 0
        for (y in lines.indices) {
            for (x in lines[y].indices) {
                val pos = Pos(x, y)
                if (path.contains(pos)) {
                    if (pos.down != null) {
                        inner = !inner
                    }

                    sb.append(pos.drawChar)
                } else if (inner) {
                    sb.append("$ANSI_GREEN_BACKGROUND $ANSI_RESET")
                    count++
                } else {
                    sb.append("░")
                }
            }
            sb.append('\n')
        }
        println(sb)
        return count
    }

    inner class Pos(val x: Int, val y: Int) {
        val char = charAt(this)
        val up: Pos? by lazy { if ("S|JL".contains(char)) Pos(x, y - 1) else null }
        val down: Pos? by lazy { if ("S|F7".contains(char)) Pos(x, y + 1) else null }
        val right: Pos? by lazy { if ("S-LF".contains(char)) Pos(x + 1, y) else null }
        val left: Pos? by lazy { if ("S-7J".contains(char)) Pos(x - 1, y) else null }

        val drawChar = when (char) {
            '-' -> '━'
            '|' -> '┃'
            '7' -> '┓'
            'J' -> '┛'
            'L' -> '┗'
            'F' -> '┏'
            'S' -> '█'
            else -> ' '
        }

        fun next(prev: Pos): Pos =
            listOfNotNull(up, down, right, left).first { it != prev }

        override fun toString() = "$x.$y=$char"

        override fun equals(other: Any?): Boolean = other is Pos && x == other.x && y == other.y
    }
}
