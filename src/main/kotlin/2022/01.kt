package `2022`

import java.io.File

fun main() {
    val lines = File("src/main/resources/2022/input02.txt").readLines()
    println(lines.sumOf { getScore(it) })
    println(lines.sumOf { getScore2(it) })
}

fun getScore(round: String): Int {
    return round[2].code.toByte() - 87 + getMatchScore(round[2], round[0])
}

fun getScore2(round: String): Int {
    return (round[2].code.toByte() - 88) * 3 + getMatchScore2(round[2], round[0])
}

fun getMatchScore2(outcome: Char, opponent: Char): Int {
    return when (outcome) {
        'X' -> if (opponent == 'A') 3 else if (opponent == 'B') 1 else 2
        'Y' -> if (opponent == 'A') 1 else if (opponent == 'B') 2 else 3
        else -> if (opponent == 'A') 2 else if (opponent == 'B') 3 else 1
    }
}

fun getMatchScore(me: Char, opponent: Char): Int {
    return when (me - opponent) {
        24, 21 -> 6
        25, 22 -> 0
        else -> 3
    }
}
