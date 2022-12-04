package aoc.day4

import aoc.loadInput
import java.io.File

/**
 * @author Dominik Hoftych
 */

fun String.asRange(): IntRange =
    if (matches("\\d+-\\d+".toRegex()))
        split("-").map(String::toInt).let { (start, end) -> IntRange(start, end) }
    else
        throw IllegalArgumentException("Invalid range: $this")

infix fun IntRange.overlapsWith(other: IntRange) = (other.first in first .. last) || (first in other.first .. other.last)

infix fun IntRange.fullyContains(other: IntRange) =
    (first <= other.first && last >= other.last) || (other.first <= first && other.last >= last)


fun File.part1() = readLines()
    .map { it.split(",").map(String::asRange) }
    .count { (first, second) -> first fullyContains second }
    .also(::println)

fun File.part2() = readLines()
    .map { it.split(",").map(String::asRange) }
    .count { (first, second) -> first overlapsWith second }
    .also(::println)

fun main() {
    with(loadInput(day = 4)) {
        part1()
        part2()
    }
}