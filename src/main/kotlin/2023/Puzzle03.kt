package `2023`.`2023`

import java.io.File

class Puzzle03 {
    fun getPartNumbersSum(lines: List<String>): Int {
        val grid: Array<CharArray> = lines.map { it.toCharArray() }.toTypedArray()
        return grid.mapIndexed() { index, row ->
            getAllNumbers(row)
                .filter { hasAdjacentSymbol(index, it, grid) }
//                .onEach { println("summing $it (${String(row).substring(it.start, it.end).toInt()})") }
                .map { String(row).substring(it.start, it.end).toInt() }
                .sum()
        }
            .sum()
    }

    fun getGearRatioSum(lines: List<String>): Int {
        val grid: Array<CharArray> = lines.map { it.toCharArray() }.toTypedArray()
        return grid.flatMapIndexed() { rowIndex, row ->
            String(row).mapIndexed { colIndex, c -> if (row[colIndex] == '*') Pair(rowIndex, colIndex) else null }
                .filterNotNull().toList()
        }
            .map { it.getAdjacentParts(grid) }
            .sum()
    }

    private fun getAllNumbers(row: CharArray): List<Number> {
        val result = ArrayList<Number>()
        var i = 0
        while (i < row.size) {
            var current = row[i]
            if (!current.isDigit() || current == '.') {
                i++
                continue
            }
            val start = i
            while (i < row.size && current.isDigit()) {
                current = row[i]
                i++
            }
            if (i == row.size && row[i - 1].isDigit()) {
                i++
            }
            result.add(Number(start, i - 1))
        }

        return result
    }

    private fun hasAdjacentSymbol(row: Int, number: Number, grid: Array<CharArray>): Boolean {

        // checks upper row
        if (row > 0) {
            if (number.start > 0 && grid[row - 1][number.start - 1].isSymbol()) {
                return true
            }
            var current = number.start
            while (current < number.end) {
                if (grid[row - 1][current].isSymbol()) {
                    return true
                }
                current++
            }
            if (number.end < grid[0].size - 1 && grid[row - 1][number.end].isSymbol()) {
                return true
            }
        }

        // checks left and right
        if (number.start > 0 && grid[row][number.start - 1].isSymbol()) {
            return true
        }
        if (number.end < grid[0].size - 1 && grid[row][number.end].isSymbol()) {
            return true
        }

        // checks lower row
        if (row < grid.size - 1) {
            if (number.start > 0 && grid[row + 1][number.start - 1].isSymbol()) {
                return true
            }
            var current = number.start
            while (current < number.end) {
                if (grid[row + 1][current].isSymbol()) {
                    return true
                }
                current++
            }
            if (number.end < grid[0].size - 1 && grid[row + 1][number.end].isSymbol()) {
                return true
            }
        }
        return false
    }

    private fun Pair<Int, Int>.getAdjacentParts(grid: Array<CharArray>): Int {

        val row = this.first
        val col = this.second
        val numbers = mutableSetOf<Int>()

        // checks upper row
        if (row > 0) {
            if (col > 0 && grid[row - 1][col - 1].isDigit()) {
                numbers.add(getNumber(row - 1, col - 1, grid))
            }
            if (grid[row - 1][col].isDigit()) {
                numbers.add(getNumber(row - 1, col, grid))
            }
            if (col < grid[0].size - 1 && grid[row - 1][col + 1].isDigit()) {
                numbers.add(getNumber(row - 1, col + 1, grid))
            }
        }

        // same row
        if (col > 0 && grid[row][col - 1].isDigit()) {
            numbers.add(getNumber(row , col - 1, grid))
        }
        if (col < grid[0].size - 1 && grid[row][col + 1].isDigit()) {
            numbers.add(getNumber(row , col + 1, grid))
        }


        // checks lower row
        if (row < grid.size - 1) {
            if (col > 0 && grid[row + 1][col - 1].isDigit()) {
                numbers.add(getNumber(row + 1, col - 1, grid))
            }
            if (grid[row + 1][col].isDigit()) {
                numbers.add(getNumber(row + 1, col, grid))
            }
            if (col < grid[0].size - 1 && grid[row + 1][col + 1].isDigit()) {
                numbers.add(getNumber(row + 1, col + 1, grid))
            }
        }


        val res =  if (numbers.size == 2) numbers.reduce { acc, it  -> it * acc } else 0
        println("nmbers=$numbers = $res")
        return res
    }

    private fun getNumber(row: Int, col: Int, grid: Array<CharArray>): Int {
        var start = col
        while (start >= 0 && grid[row][start].isDigit()) {
            start--
        }
        start++
        var end = col
        while (end < grid[row].size && grid[row][end].isDigit()) {
            end++
        }
        end --
//        println("return ${String(grid[row]).substring(start, end + 1).toInt()}")
        return String(grid[row]).substring(start, end + 1).toInt()
    }

    private fun Char.isSymbol(): Boolean = !this.isDigit() && this != '.'
}

data class Number(val start: Int, val end: Int) {
    override fun toString() = "[$start, $end]"
}

fun main() {
    val lines = File("src/main/resources/2023/03.txt").readLines()
    println("Part 1: " + Puzzle03().getPartNumbersSum(lines))
    println("Part 2: " + Puzzle03().getGearRatioSum(lines))
}