fun main() {
    val reDigit = "\\d+".toRegex()

    fun findWinningOptions(maxTime: Long, bestDistance: Long): Int {
        var time = 1
        var winningOptions = 0
        while (time < maxTime) {
            val timeRemaining = maxTime - time
            val distance = timeRemaining * time // time == speed
            if (distance > bestDistance) winningOptions++
            time++
        }

        return winningOptions
    }

    fun part1(input: List<String>): Int {
        val times = reDigit.findAll(input[0]).map { match -> match.value.toLong() }
        val distances = reDigit.findAll(input[1]).map { match -> match.value.toLong() }
        val timesAndDistances = times.zip(distances)

        var result = 1
        for ((time, distance) in timesAndDistances) {
            result *= findWinningOptions(time, distance)
        }

        return result
    }


    fun part2(input: List<String>): Int {
        val time = reDigit.findAll(input[0]).map { match -> match.value }.joinToString(separator = "").toLong()
        val distance = reDigit.findAll(input[1]).map { match -> match.value }.joinToString(separator = "").toLong()
        return findWinningOptions(time, distance)
    }

    val input = readInput("wait_for_it")
    part1(input).println()
    part2(input).println()
}