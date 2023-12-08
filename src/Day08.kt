fun main() {
    val reWord = "\\w+".toRegex()
    fun buildDirections(input: List<String>): Map<String, Pair<String, String>> {
        val directions: MutableMap<String, Pair<String, String>> = mutableMapOf()
        for (line in input) {
            val (source, left, right) = reWord.findAll(line).map { it.value }.toList()
            directions[source] = Pair(left, right)
        }

        return directions.toMap()
    }

    fun getNextStep(instruction: Char, step: String, directions: Map<String, Pair<String, String>>): String {
        val (left, right) = directions[step]!!
        return if (instruction == 'L') {
            left
        } else {
            right
        }
    }

    fun part1(input: List<String>): Int {
        val instructions = input[0].toList()
        val directions = buildDirections(input.subList(2, input.size))

        var step = "AAA"
        var numOfSteps = 0
        while (step != "ZZZ") {
            val thisInstruction = instructions[numOfSteps % instructions.size]
            step = getNextStep(thisInstruction, step, directions)
            numOfSteps++

        }

        return numOfSteps
    }

    tailrec fun gcd(a: Long, b: Long): Long = if (b == 0L) a else gcd(b, a % b)
    fun lcm(a: Long, b: Long) = a * b / gcd(a, b)


    // Function to find the LCM of a list of numbers
    fun lcmOfList(numbers: List<Long>): Long {
        require(numbers.isNotEmpty()) { "List must not be empty" }

        // Initialize lcmResult with the first element of the list
        var lcmResult = numbers[0]

        // Iterate through the list and calculate LCM iteratively
        for (i in 1 until numbers.size) lcmResult = lcm(lcmResult, numbers[i])

        return lcmResult
    }


    fun part2(input: List<String>): Long {
        val instructions = input[0].toList()
        val directions = buildDirections(input.subList(2, input.size))

        val steps = directions.keys.filter { it.endsWith("A") }
        steps.println()
        val allStepCounts = mutableListOf<Long>()

        for (element in steps) {
            var step = element
            var stepCount: Long = 0
            while (!step.endsWith("Z")) {
                val thisInstruction = instructions[(stepCount % instructions.size.toLong()).toInt()]
                step = getNextStep(thisInstruction, step, directions)
                stepCount++
            }
            allStepCounts.add(stepCount)
        }
        allStepCounts.println()
        return lcmOfList(allStepCounts)
    }

    val input = readInput("haunted_wasteland")
    part1(input).println()
    part2(input).println()
}