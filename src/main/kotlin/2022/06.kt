package `2022`

import java.io.File

fun main() {
    val marker = StringBuffer()
    File("src/main/resources/2022/input06.txt")
        .readBytes()
        .forEachIndexed { index, byte ->
            if (marker.contains(byte.toInt().toChar())) {
                marker.delete(0, marker.length)
            }
            marker.append(byte.toInt().toChar())
            if (marker.length == 4 || marker.length == 14) {
                println("Index[${marker.length}]=${index+1}")
            }
        }
}
