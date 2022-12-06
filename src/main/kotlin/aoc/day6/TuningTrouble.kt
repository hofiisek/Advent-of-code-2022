package aoc.day6

import aoc.distinctSet
import aoc.loadInput
import java.io.File

/**
 * https://adventofcode.com/2022/day/6
 *
 * @author Dominik Hoftych
 */

fun File.part1() = readLines()
    .first()
    .windowed(4)
    .indexOfFirst { chunk -> chunk.distinctSet().size == 4 }
    .let { it + 4}
    .also(::println)


fun File.part2() = readLines()
    .first()
    .windowed(14)
    .indexOfFirst { chunk -> chunk.distinctSet().size == 14  }
    .let { it + 14}
    .also(::println)


fun main() {
    with(loadInput(day = 6)) {
        part1()
        part2()
    }
}