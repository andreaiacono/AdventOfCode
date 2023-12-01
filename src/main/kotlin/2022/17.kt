package `2022`

import java.io.File

fun main() {
    val current = StringBuffer("")
    val dirs = mutableMapOf<String, Long>()
    var currentDirSize = 0L
    File("src/main/resources/2022/input17.txt")
        .readLines()
        .drop(1)
        .filter { !it.startsWith("$ ls") }
        .forEach {
            if (it.startsWith("$ cd")) {
                val newDir = it.substring(5)
                dirs[current.toString()] = currentDirSize
                if (newDir == "..") {
                    println("$current: $currentDirSize")
                    current.delete(current.lastIndexOf("/"), current.length)
                }
                else {
                    current.append("/").append(newDir)
                }
//                println(current)
                currentDirSize = 0
            }
            else if (it[0].isDigit()) {
                val fileSize = it.split(" ")[0].toLong()
                currentDirSize += fileSize
                println("$current add $fileSize: $currentDirSize" )
            }
        }
    println(dirs)
}

