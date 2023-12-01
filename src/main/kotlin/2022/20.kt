package `2022`

import java.io.File
import java.lang.IndexOutOfBoundsException
import java.util.LinkedList

fun main() {
    val list = LinkedList<Int>()
    list.addAll(File("src/main/resources/2022/input20.txt").readLines().map { it.toInt() })
    val copy = LinkedList(list)
    (1..list.size).forEach {
        move(list, it, copy)
    }
    println(copy)
}

fun move(list: LinkedList<Int>, index: Int, copy: LinkedList<Int>) {
    val n: Int = list[index]
    val newIndex = n % 5000
    copy.removeAt(index)
    copy.add(newIndex, list[index])
}

