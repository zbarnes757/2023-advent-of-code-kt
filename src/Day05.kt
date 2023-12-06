fun main() {
    val reDigit = "\\d+".toRegex()

    fun computeRange(start: Long, rangeSize: Long): OpenEndRange<Long> {
        return start..<(start + rangeSize)
    }

    fun populateSeeds(line: String, seedList: MutableList<Long>) {
        reDigit.findAll(line).forEach { match -> seedList.add(match.value.toLong()) }
    }

    fun parseMap(
        mapStart: Int,
        input: List<String>,
        seedList: MutableList<Long>,
        alreadyModified: MutableSet<Int> = mutableSetOf()
    ): Int {
        if (mapStart == input.size || input[mapStart] == "") return mapStart + 1
        val (destinationStart, sourceStart, range) = reDigit.findAll(input[mapStart])
            .map { match -> match.value.toLong() }.toList()

        for ((idx, seed) in seedList.withIndex()) {
            if (alreadyModified.contains(idx)) continue
            val sourceRange = computeRange(sourceStart, range)
            if (seed in sourceRange) {
                val difference = destinationStart - sourceStart
                seedList[idx] = seed + difference
                alreadyModified.add(idx)
            }
        }

        return parseMap(mapStart + 1, input, seedList, alreadyModified)
    }

    fun part1(input: List<String>): Long {
        val seedList = mutableListOf<Long>()
        populateSeeds(input[0], seedList)
        var idx = 2

        while (idx < input.size) {
            if (input[idx] == "") {
                idx++
                continue
            } else if (input[idx].endsWith("map:")) {
                idx = parseMap(idx + 1, input, seedList)
            }
        }

        return seedList.min()
    }

    fun populateSeedsFromRanges(line: String, seedList: MutableList<Long>) {
        val seedRanges = reDigit.findAll(line).map { match -> match.value.toLong() }.chunked(2)
            .map { (start, range) -> computeRange(start, range) }
        for (seedRange in seedRanges) {
            var seed = seedRange.start
            while (seed < seedRange.endExclusive) {
                seedList.add(seed)
                seed++
            }
        }
    }


    fun part2(input: List<String>): Long {
        val seedList = mutableListOf<Long>()
        populateSeedsFromRanges(input[0], seedList)
        var idx = 2

        while (idx < input.size) {
            if (input[idx] == "") {
                idx++
                continue
            } else if (input[idx].endsWith("map:")) {
                idx = parseMap(idx + 1, input, seedList)
            }
        }

        return seedList.min()
    }

    val input = readInput("seed_instructions")
    part1(input).println()
    part2(input).println()
}