fun main() {
    val spelledOutNumberMapping = mapOf(
        "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5",
        "six" to "6", "seven" to "7", "eight" to "8", "nine" to "9"
    )

    fun replaceWordNumbersInLine(line: String): String {
        val newLine = StringBuilder(line)
        for (word in spelledOutNumberMapping.keys) {
            val reWord = word.toRegex()
            val matches = reWord.findAll(line)
            for (match in matches) {
                val mid = (match.range.last + match.range.first) / 2
                newLine[mid] = spelledOutNumberMapping[match.value]!!.single()
            }
        }

        return newLine.toString()
    }


    fun findFirstDigit(line: String): String {
        return line.first { char -> char.isDigit() }.toString()
    }

    fun computeValueForLine(line: String): Int {
        val leftDigit = findFirstDigit(line)
        val rightDigit = findFirstDigit(line.reversed())
        return "$leftDigit$rightDigit".toInt()
    }

    fun part1(input: List<String>): Int {
        return input.fold(0) { acc, line -> acc + computeValueForLine(line) }
    }

    fun part2(input: List<String>): Int {
        return input.fold(0) { acc, line ->
            val parsedLine = replaceWordNumbersInLine(line)
            acc + computeValueForLine(parsedLine)
        }
    }

    val input = readInput("calibration_values")
    part1(input).println()
    part2(input).println()
}
