fun main() {
  val AFTER = "after"
  val BEFORE = "before"

  fun addBeforeAndAfterValuesFor(
    parentNumber: Int, numberAfter: Int, rules: MutableMap<Int, MutableMap<String, MutableList<Int>>>, mode: String
  ) {
    val rulesForX = rules[parentNumber]
    if (rulesForX == null) {
      rules[parentNumber] = mutableMapOf(
        Pair(if (mode == AFTER) BEFORE else AFTER, mutableListOf()), Pair(mode, mutableListOf(numberAfter))
      )
    } else {
      rules[parentNumber]!![mode]!!.add(numberAfter)
    }
  }

  fun readValues(input: List<String>): Pair<MutableMap<Int, MutableMap<String, MutableList<Int>>>, MutableList<MutableList<Int>>> {
    val rules = mutableMapOf<Int, MutableMap<String, MutableList<Int>>>()
    val numberOrders = mutableListOf<MutableList<Int>>()
    var part = 0
    for (row in input) {
      if (part == 0) {
        if (row.isBlank()) {
          part++
        } else {
          val (x, y) = row.split("|").map { it.toInt() }
          addBeforeAndAfterValuesFor(x, y, rules, AFTER)
          addBeforeAndAfterValuesFor(y, x, rules, BEFORE)
        }
      } else {
        numberOrders.add(row.split(",").map { it.toInt() }.toMutableList())
      }
    }
    return Pair(rules, numberOrders)
  }

  fun getOrderValue(order: List<Int>, rules: MutableMap<Int, MutableMap<String, MutableList<Int>>>): Int {
    for (i in order.indices) {
      val checkValue = order[i]
      for (j in order.indices) {
        if (i == j) continue
        if (i < j) {
          val numberAfter = order[j]
          if (rules[numberAfter]!![AFTER]!!.contains(checkValue)) return 0
        } else {
          val numberBefore = order[j]
          if (rules[numberBefore]!![BEFORE]!!.contains(checkValue)) return 0
        }
      }
    }
    return order[order.size / 2]
  }

  fun calculateScore(numberOrders: MutableList<MutableList<Int>>, rules: MutableMap<Int, MutableMap<String, MutableList<Int>>>): Int {
    var score = 0
    for (order in numberOrders) {
      score += getOrderValue(order, rules)
    }
    return score
  }

  fun part1(input: List<String>): Int {
    val (rules, numberOrders) = readValues(input)
    return calculateScore(numberOrders, rules)
  }

  fun getCorrectOrderNew(incorrectOrder: MutableList<Int>, rules: MutableMap<Int, MutableMap<String, MutableList<Int>>>): MutableList<Int> {
    val correctOrder = mutableListOf<Int>()
    val orderSize = incorrectOrder.size
    while (correctOrder.size != orderSize) {
      for (checkNum in incorrectOrder) {
        if (correctOrder.contains(checkNum)) continue
        for (j in incorrectOrder.indices) {
          val checkAgainst = incorrectOrder[j]
          if (correctOrder.contains(checkAgainst)) {
            if (j == orderSize - 1) {
              correctOrder.add(checkNum)
              break
            } else {
              continue
            }
          }
          if (rules[checkAgainst]!![AFTER]!!.contains(checkNum)) break
          if (j == orderSize - 1) {
            correctOrder.add(checkNum)
          }
        }
      }
    }
    return correctOrder
  }

  fun getCorrectScore(incorrectOrders: MutableList<MutableList<Int>>, rules: MutableMap<Int, MutableMap<String, MutableList<Int>>>): Int {
    var score = 0
    for (order in incorrectOrders) {
      score += getOrderValue(getCorrectOrderNew(order, rules), rules)
    }
    return score
  }

  fun part2(input: List<String>): Int {
    val (rules, numberOrders) = readValues(input)
    val incorrectOrders = mutableListOf<MutableList<Int>>()
    for (order in numberOrders) {
      if (getOrderValue(order, rules) == 0) {
        incorrectOrders.add(order)
      }
    }
    return getCorrectScore(incorrectOrders, rules)
  }

  val testInput = readInput("Day05_test")
  check(part1(testInput) == 143)
  check(part2(testInput) == 123)

  val input = readInput("Day05")
  part1(input).println()
  part2(input).println()
}
