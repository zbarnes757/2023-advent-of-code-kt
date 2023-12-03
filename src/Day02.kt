fun main() {
    val partOneDesiredCombos = mapOf("red" to 12, "green" to 13, "blue" to 14)

    fun parseGameID(line: String): Int {
        return line.split(":").first().replace("Game ", "").toInt()
    }

    fun setPossible(set: String, desiredCombos: Map<String, Int>): Boolean {
        set.split(",").forEach { s ->
            val (count, color) = s.trim().split(" ")
            if (count.toInt() > desiredCombos[color]!!) return false
        }

        return true
    }

    fun gameHasDesiredCombos(line: String, desiredCombos: Map<String, Int>): Boolean {
        return line.split(":").last().split(";").all { set -> setPossible(set, desiredCombos) }
    }

    fun findMinimumSetPower(game: String): Int {
        val sets = game.split(":").last().split(";")
        val minimumSet = mutableMapOf("red" to 0, "blue" to 0, "green" to 0)
        for (set in sets) {
            set.split(",").forEach { s ->
                val (countStr, color) = s.trim().split(" ")
                val count = countStr.toInt()
                if (minimumSet[color]!! < count) minimumSet[color] = count
            }
        }

        return minimumSet.values.fold(1) { power, count -> power * count }
    }


    fun part1(input: List<String>): Int {
        return input.fold(0) { score, line ->
            val gameID = parseGameID(line)
            if (gameHasDesiredCombos(line, partOneDesiredCombos)) {
                score + gameID
            } else {
                score
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { score, line -> score + findMinimumSetPower(line) }
    }

    val input = readInput("cube_conundrum")
    part1(input).println()
    part2(input).println()
}
