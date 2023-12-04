fun main() {
    fun isSymbol(item: Char): Boolean {
        return !item.isDigit() and (item != '.')
    }

    fun buildNumber(
        rowStart: Int,
        colStart: Int,
        row: List<Char>,
        seen: MutableSet<Pair<Int, Int>>,
    ): Int {
        var result = row[colStart].toString()
        var idx = colStart - 1
        while (idx >= 0 && row[idx].isDigit()) {
            val digit = row[idx].toString()
            result = "$digit$result"
            seen.add(Pair(rowStart, idx))
            idx--
        }

        idx = colStart + 1
        while (idx < row.size && row[idx].isDigit()) {
            val digit = row[idx].toString()
            result = "$result$digit"
            seen.add(Pair(rowStart, idx))
            idx++
        }

        return result.toInt()
    }

    fun searchForMatches(
        rowStart: Int,
        colStart: Int,
        gearMatrix: List<List<Char>>,
        seen: MutableSet<Pair<Int, Int>>,
        maxRows: Int,
        maxColumns: Int
    ): Int {
        if (rowStart < 0 || rowStart >= maxRows || colStart < 0 || colStart >= maxColumns) return 0
        if (seen.contains(Pair(rowStart, colStart))) return 0

        if (!gearMatrix[rowStart][colStart].isDigit()) return 0
        seen.add(Pair(rowStart, colStart))
        return buildNumber(rowStart, colStart, gearMatrix[rowStart], seen)
    }

    val searchPositions =
        listOf(Pair(0, -1), Pair(0, 1), Pair(-1, 0), Pair(1, 0), Pair(-1, -1), Pair(-1, 1), Pair(1, -1), Pair(1, 1))

    fun part1(input: List<String>): Int {
        val seen = mutableSetOf<Pair<Int, Int>>()
        val gearMatrix = input.map { line -> line.toList() }
        val maxRows = gearMatrix.size
        val maxColumns = gearMatrix.first().size

        var result = 0
        for ((rowIdx, row) in gearMatrix.withIndex()) {
            for ((colIdx, item) in row.withIndex()) {
                if (isSymbol(item)) {
                    for ((rowModifier, colModifier) in searchPositions) {
                        result += searchForMatches(
                            rowIdx + rowModifier,
                            colIdx + colModifier,
                            gearMatrix,
                            seen,
                            maxRows,
                            maxColumns
                        )
                    }
                }
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val seen = mutableSetOf<Pair<Int, Int>>()
        val gearMatrix = input.map { line -> line.toList() }
        val maxRows = gearMatrix.size
        val maxColumns = gearMatrix.first().size

        var result = 0
        for ((rowIdx, row) in gearMatrix.withIndex()) {
            for ((colIdx, item) in row.withIndex()) {
                if (item == '*') {
                    val numbersNearGear = mutableListOf<Int>()
                    for ((rowModifier, colModifier) in searchPositions) {
                        val match = searchForMatches(
                            rowIdx + rowModifier,
                            colIdx + colModifier,
                            gearMatrix,
                            seen,
                            maxRows,
                            maxColumns
                        )
                        if (match > 0) numbersNearGear.add(match)
                    }
                    if (numbersNearGear.size == 2) {
                        val (first, second) = numbersNearGear
                        result += first * second
                    }
                }
            }
        }

        return result
    }

    val input = readInput("gear_ratios")
    part1(input).println()
    part2(input).println()
}