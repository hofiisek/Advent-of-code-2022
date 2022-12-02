package aoc.aoc.day01

import aoc.loadInput
import java.io.File

/**
 * https://adventofcode.com/2022/day/1
 *
 * @author Dominik Hoftych
 */

//@JvmInline
//value class Elf(var calories: Int = 0)

fun File.part1() = readLines()
    .fold(listOf<Int>() to 0) { (elves, sumCalories), foodCalories ->
        if (foodCalories.isBlank())
            elves + sumCalories to 0
        else
            elves to sumCalories + foodCalories.toInt()
    }
    .first
    .maxBy { it }
    .also(::println)

fun File.part2() = readLines()
    .fold(listOf<Int>() to 0) { (elves, sumCalories), foodCalories ->
        if (foodCalories.isBlank())
            elves + sumCalories to 0
        else
            elves to sumCalories + foodCalories.toInt()
    }
    .first
    .sorted()
    .takeLast(3)
    .sum()
    .also(::println)

fun main() {
    with(loadInput(day = 1)) {
        part1()
        part2()
    }
}