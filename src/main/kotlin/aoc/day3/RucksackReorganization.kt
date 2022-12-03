package aoc.day3

import aoc.distinctSet
import aoc.index
import aoc.loadInput
import aoc.splitInHalf
import java.io.File

/**
 * https://adventofcode.com/2022/day/3
 *
 * @author Dominik Hoftych
 */

fun File.part1() = readLines()
    .map(String::splitInHalf)
    .map { (firstHalf, secondHalf) -> firstHalf.filter(secondHalf::contains) }
    .map(List<Char>::single)
    .sumOf { it.index() + 1 }
    .also(::println)

fun File.part2() = readLines()
    .chunked(3)
    .map { it.map(String::distinctSet) }
    .map { (first, second, third) -> first.filter(second::contains).filter(third::contains) }
    .map(List<Char>::single)
    .sumOf { it.index() + 1 }
    .also(::println)


fun main() {
    with(loadInput(day = 3)) {
        part1()
        part2()
    }
}