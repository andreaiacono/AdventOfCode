package `2023`

import java.io.File
import kotlin.math.pow

class Puzzle07 {
    fun partOne(lines: List<String>): Long {
        val hands = loadHands(lines)
            .sortedWith(compareBy({ it.getType().ordinal }, { it.getValue() }))
//        println(hands.map { "$it [${it.getType()}]" }.joinToString("\n"))
        return hands.mapIndexed { index, hand -> hand.bid * (hands.size - index) }.sum()
    }

    fun partTwo(lines: List<String>): Long {
        val hands = loadHands(lines)
            .sortedWith(compareBy({ it.getTypeWithJoker().ordinal }, { it.getValueWithJoker() }))

        println(hands.map { "$it [${it.getTypeWithJoker()}]" }.joinToString("\n"))
        return hands.mapIndexed { index, hand -> hand.bid * (hands.size - index) }.sum()
    }

    private fun loadHands(lines: List<String>) =
        lines
            .map { Hand(it.split(" ")[0].trim().toCharArray(), it.split(" ")[1].trim().toLong()) }


    data class Hand(val cards: CharArray, val bid: Long) {

        fun getValue() =
            cards.mapIndexed { index, c -> (Label.valueOf(c.toString()).ordinal + 1) * 15.0.pow(5 - index) }.sum()

        fun getValueWithJoker() =
            cards.mapIndexed { index, c -> (LabelWithJoker.valueOf(c.toString()).ordinal + 1) * 15.0.pow(5 - index) }
                .sum()

        fun getType(): Type {
            val cardsMap: Map<Label, Int> = getCardsFrequency()
            return when {
                cardsMap.any { it.value == 5 } -> Type.FIVE_OF_A_KIND
                cardsMap.any { it.value == 4 } -> Type.FOUR_OF_A_KIND
                cardsMap.any { it.value == 3 } && cardsMap.any { it.value == 2 } -> Type.FULL_HOUSE
                cardsMap.any { it.value == 3 } -> Type.THREE_OF_A_KIND
                cardsMap.filter { it.value == 2 }.size == 2 -> Type.TWO_PAIRS
                cardsMap.any { it.value == 2 } -> Type.PAIR
                cardsMap.all { it.value < 2 } -> Type.HIGH_CARD
                else -> throw Exception("Type not found for ${cards.contentToString()}")
            }
        }

        fun getTypeWithJoker(): Type {
            if (cards.all { Label.valueOf(it.toString()) != Label.J }) {
                return getType()
            }

            val normalType = getType()
            return when {
                normalType == Type.FIVE_OF_A_KIND || normalType == Type.FOUR_OF_A_KIND -> return Type.FIVE_OF_A_KIND
                normalType == Type.FULL_HOUSE -> return Type.FIVE_OF_A_KIND
                normalType == Type.THREE_OF_A_KIND -> return Type.FOUR_OF_A_KIND
                normalType == Type.TWO_PAIRS -> return if (getCardsFrequency()[Label.J] == 2) Type.FOUR_OF_A_KIND else Type.FULL_HOUSE
                normalType == Type.PAIR -> return Type.THREE_OF_A_KIND
                normalType == Type.HIGH_CARD -> return Type.PAIR
                else -> throw Exception("Type not found for ${cards.contentToString()}")
            }
        }

        private fun getCardsFrequency() = cards.map { Label.valueOf(it.toString()) }.groupingBy { it }.eachCount()
    }

    enum class Label {
        `A`, `K`, `Q`, `J`, `T`, `9`, `8`, `7`, `6`, `5`, `4`, `3`, `2`
    }

    enum class LabelWithJoker {
        `A`, `K`, `Q`, `T`, `9`, `8`, `7`, `6`, `5`, `4`, `3`, `2`, `J`
    }

    enum class Type {
        FIVE_OF_A_KIND,
        FOUR_OF_A_KIND,
        FULL_HOUSE,
        THREE_OF_A_KIND,
        TWO_PAIRS,
        PAIR,
        HIGH_CARD
    }

}

fun main() {
    val lines = File("src/main/resources/2023/07.txt").readLines()
    println("Part 1: " + Puzzle07().partOne(lines))
    println("Part 2: " + Puzzle07().partTwo(lines))
}


