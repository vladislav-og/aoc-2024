import kotlin.streams.toList

fun main() {

  fun constructMap(input: List<String>): MutableList<MutableList<String>> {
    return input.stream().map { row ->
      row.split("").filter { it.isNotBlank() }.toMutableList()
    }.toList()
  }

  fun findStartPost(gameMap: List<List<String>>): Pair<Int, Int> {
    for (y in gameMap.indices) {
      for (x in gameMap[y].indices) {
        if (gameMap[y][x] == "^") {
          return Pair(x, y)
        }
      }
    }
    throw RuntimeException("start not found")
  }

  fun printMap(gameMap: MutableList<MutableList<String>>) {
    for (row in gameMap) {
      println(row)
    }
    println()
  }

  data class Direction(var y: Int, var x: Int = 0) {}

  fun moveOnMap(startPosX: Int, startPosY: Int, gameMap: MutableList<MutableList<String>>) {
    var direction = Direction(-1, 0) // y, x
    var posX = startPosX
    var posY = startPosY
    while (true) {
      if (posX < 0 || posX >= gameMap[0].size) break
      if (posY < 0 || posY >= gameMap.size) break
      if (posX + direction.x < 0 || posX + direction.x>= gameMap.size) break
      if (posY + direction.y < 0 || posY + direction.y >= gameMap.size) break
      if (gameMap[posY + direction.y][posX + direction.x] == "#") {
        if (direction.y == -1 && direction.x == 0) {
          direction = Direction(0, 1)
        } else if (direction.y == 0 && direction.x == 1) {
          direction = Direction(1, 0)
        } else if (direction.y == 1 && direction.x == 0) {
          direction = Direction(0, -1)
        } else if (direction.y == 0 && direction.x == -1) {
          direction = Direction(-1, 0)
        }
      }
      gameMap[posY][posX] = "X"
      posX += direction.x
      posY += direction.y
      printMap(gameMap)
    }
  }

  fun calculateScore(gameMap: MutableList<MutableList<String>>): Int {
    var score = 0
    gameMap.forEach{
      it.forEach{
        value -> if (value == "X") score += 1
      }
    }
    return score + 1
  }

  fun part1(input: List<String>): Int {
    val gameMap: MutableList<MutableList<String>> = constructMap(input)
    val (startPosX, startPosY) = findStartPost(gameMap)
    moveOnMap(startPosX, startPosY, gameMap)
    return calculateScore(gameMap)
  }

  fun part2(input: List<String>): Int {
    return 0
  }

  val testInput = readInput("Day06_test")
//  check(part1(testInput) == 41)
//  check(part2(testInput) == 123)

  val input = readInput("Day06")
  part1(input).println()
//  part2(input).println()
}
