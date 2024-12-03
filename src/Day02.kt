fun main() {
    fun isLevelOk(levels: List<Int>): Boolean {
        var curLevel: Int? = null
        val curSlope = if (levels[0] > levels[1]) -1 else 1
        var counter = 0
        for (i in levels.indices) {
            val level = levels[i]
            if (curLevel == level) break

            if (curLevel == null) {
                curLevel = level
                counter++
                continue
            } else if (curLevel > level && curLevel - 3 <= level && curSlope == -1) {
                curLevel = level
            } else if (curLevel < level && curLevel + 3 >= level && curSlope == 1) {
                curLevel = level
            } else {
                break
            }
            counter++
        }
        return counter == levels.size
    }


    fun part1(input: List<String>): Int {
        var score = 0
        for (inputRow in input) {
            val levels = inputRow.split(" ").map { it.toInt() }.toList()
            if (isLevelOk(levels)) score++
        }
        println(score)
        return score
    }


    fun part2(input: List<String>): Int {
        var score = 0
        for (inputRow in input) {
            val levels = inputRow.split(" ").map { it.toInt() }.toList()
            if (!isLevelOk(levels)) {
                for (removeItem in levels.indices) {
                    val newLevels = levels.toMutableList()
                    newLevels.removeAt(removeItem)
                    if (isLevelOk(newLevels)) {
                        score++
                        break
                    }
                }
            } else {
                score++
            }
        }
        return score
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part2(testInput) == 2)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
