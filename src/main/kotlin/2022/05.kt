package `2022`

import java.io.File
import java.util.*
import kotlin.collections.ArrayDeque

fun main() {
    val input = File("src/main/resources/2022/input05.txt").readLines()
    val stacks =  (1..9).map { ArrayDeque<Char>() }.toList()

    input.take(8).reversed().forEachIndexed { index, line ->
        for (i in 1..33 step 4) {
            if (line[i].isLetter()) {
                stacks[(i - 1) / 4].add(line[i])
            }
        }
    }
    input.drop(10).forEach { execute(it, stacks) }
    val tops = stacks.map { it.last() }.toCharArray()
    println(tops)

    val multiStacks =  (1..9).map { ArrayDeque<Char>() }.toList()
    input.take(8).reversed().forEachIndexed { index, line ->
        for (i in 1..33 step 4) {
            if (line[i].isLetter()) {
                multiStacks[(i - 1) / 4].add(line[i])
            }
        }
    }
    input.drop(10).forEach { multiExecute(it, multiStacks) }
    val multiTops = multiStacks.map { it.last() }.toCharArray()
    println(multiTops)
}

fun multiExecute(move: String, stacks: List<ArrayDeque<Char>>) {
    val times = move.split(" ")[1].toInt()
    val from = move.split(" ")[3].toInt()-1
    val to = move.split(" ")[5].toInt()-1
    val crates = (1..times).map { stacks[from].removeLast() }.toList().reversed()
    crates.forEach { stacks[to].add(it) }
}

fun execute(move: String, stacks: List<ArrayDeque<Char>>) {
    val times = move.split(" ")[1].toInt()
    val from = move.split(" ")[3].toInt()-1
    val to = move.split(" ")[5].toInt()-1
    (1..times).forEach { stacks[to].add(stacks[from].removeLast()) }
}
