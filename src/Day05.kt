val names = listOf("soil", "fertilizer", "water", "light", "temperature", "humidity", "location")
fun main() {
    val lines = readInput("Day05")

    val seeds = lines[0].substring(7).split(" ").map(String::toLong)

    val maps = mutableListOf<List<Mapping>>()
    lateinit var currentMappings: MutableList<Mapping>

    for (i in 2..<lines.size) {
        val line = lines[i].trim()
        if (line.endsWith("map:")) {
            currentMappings = mutableListOf()
            maps.add(currentMappings)
        } else if (line.isNotBlank()) {
            val (dest, src, range) = line.split(" ").map(String::toLong)
            val offset = dest - src
            currentMappings.add(Mapping(src, src + range - 1, offset))
        }
    }
    println("Mappings imported")

    var minLocation = Long.MAX_VALUE
    for (si in seeds.indices step 2) {
        val start = seeds[si]
        val end = start + seeds[si + 1]

        println("Processing seed range ${si / 2 + 1} of ${seeds.size / 2}, ${end - start} seeds")
        for (seed in start..end) {
//            print("seed $seed")
            var currentId = seed
            maps.forEachIndexed { i, mappings ->
//                val name = names[i]
                val mapping = mappings.find { it.start <= currentId && it.end >= currentId }
                mapping?.run { currentId += offset }
//                print(" -> $name $currentId")
                if (i == 6 && minLocation > currentId) {
                    minLocation = currentId
                }
            }
//            println()
        }
    }

    println("minLocation: $minLocation")
}

data class Mapping(
    val start: Long,
    val end: Long,
    val offset: Long,
)