import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val leftSideList = mutableListOf<Int>()
        val rightSideList = mutableListOf<Int>()
        for (row in input) {
            val (leftNum, rightNum) = row.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            leftSideList.add(leftNum)
            rightSideList.add(rightNum)
        }

        val leftSortedList = leftSideList.sorted()
        val rightSortedList = rightSideList.sorted()
        var distance = 0
        for ( i in leftSideList.indices) {
            val curValue = abs(leftSortedList.elementAt(i) - rightSortedList.elementAt(i))
            distance += curValue
        }

        return distance
    }

    fun part2(input: List<String>): Int {
        val leftSideList = mutableListOf<Int>()
        val rightSideList = mutableMapOf<Int, Int>()
        for (row in input) {
            val (leftNum, rightNum) = row.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
            leftSideList.add(leftNum)
            val curValue = rightSideList[rightNum]
            if (curValue == null) {
                rightSideList[rightNum] = 1
            } else {
                rightSideList[rightNum] = curValue + 1
            }
        }

        var result = 0
        for (num in leftSideList) {
            if (rightSideList[num] != null) {
                result += num * rightSideList[num]!!
            }
        }

        return leftSideList.map {
            if (rightSideList[it] == null) {
                0
            } else {
                rightSideList[it]!! * it
            }
        }.sum()
    }

    // Test if implementation meets criteria from the description, like:
    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
