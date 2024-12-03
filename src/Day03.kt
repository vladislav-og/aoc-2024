fun main() {

  fun part1(input: List<String>): Int {
    var score = 0
    for (row in input) {
      val r = Regex("mul\\(\\d+,\\d+\\)")
      val matchedMultiplications = r.findAll(row).map { it.value }.toList()
      for (match in matchedMultiplications) {
        println(match)
        val numbers = Regex("\\d+").findAll(match).map { it.value }.toList()
        val value = numbers[0].toInt() * numbers[1].toInt()
        score += value
      }
    }

    return score
  }


  fun part2(input: List<String>): Int {
    var score = 0
    var shouldTakeIntoAccount = true
    for (row in input) {
      val r = Regex("mul\\(\\d+,\\d+\\)|don't\\(\\)|do\\(\\)")
      val matchedMultiplications = r.findAll(row).map { it.value }.toList()
      println("total matches ${matchedMultiplications.size}")
      for (match in matchedMultiplications) {
        if (match == "don't()") {
          shouldTakeIntoAccount = false
        } else if (match == "do()") {
          shouldTakeIntoAccount = true
        } else {
          if (shouldTakeIntoAccount) {
            val numbers = Regex("\\d+").findAll(match).map { it.value }.toList()
            val value = numbers[0].toInt() * numbers[1].toInt()
            score += value
          } else {
            println(match)
          }
        }
      }
    }

    return score  }

  // Or read a large test input from the `src/Day01_test.txt` file:
  val testInput = readInput("Day03_test")
  check(part1(testInput) == 161)
  check(part2(testInput) == 48)

  val input = readInput("Day03")
  part1(input).println()
  part2(input).println()
}
