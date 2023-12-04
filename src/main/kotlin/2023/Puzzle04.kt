package `2023`

import java.io.File
import java.lang.Math.pow

class Puzzle04 {
    fun getCardsPoints(cards: List<String>) = cards.map { it.toCard() }.map { it.getScore() }.sum()

    fun getScoreCards(cardsStrings: List<String>): Int {
        val cardsNums = MutableList(cardsStrings.size) { 1 }
        cardsStrings.map { it.toCard() }
            .toMutableList()
            .forEachIndexed { cardIndex, card ->
                (0..card.getIntersectionSize())
                    .forEach { cardsNums[cardIndex + it + 1] += cardsNums[cardIndex] }
            }
        return cardsNums.sum()
    }

    data class Card(val nums: List<Int>, val wins: List<Int>) {
        fun getScore() = pow(2.0, this.getIntersectionSize().toDouble()).toInt()
        fun getIntersectionSize() = this.nums.intersect(this.wins).size - 1
    }

    private fun String.toCard(): Card = Card(
        this.substring(9).split("|")[0].splitAndParse(),
        this.substring(9).split("|")[1].splitAndParse()
    )
    private fun String.splitAndParse() = this.trim().split("\\s+".toRegex()).map { it.toInt() }.toList()
}

fun main() {
    val lines = File("src/main/resources/2023/04.txt").readLines()
    println("Part 1: " + Puzzle04().getCardsPoints(lines))
    println("Part 2: " + Puzzle04().getScoreCards(lines))
}


