package aoc

import java.io.File

/**
 * @author Dominik Hoftych
 */

fun loadInput(filename: String = "input.txt", day: Int) =
    File("src/main/kotlin/aoc/day$day/$filename")