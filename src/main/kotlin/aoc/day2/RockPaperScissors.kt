package aoc.aoc.day2

import aoc.loadInput
import java.io.File

/**
 * https://adventofcode.com/2022/day/2
 *
 * @author Dominik Hoftych
 */

sealed class Outcome(val score: Int)
object Lose : Outcome(0)
object Draw : Outcome(3)
object Win : Outcome(6)

sealed class Shape(val score: Int)
object Rock : Shape(1)
object Paper : Shape(2)
object Scissors : Shape(3)

fun String.asShape() = when (this) {
    "A", "X" -> Rock
    "B", "Y" -> Paper
    "C", "Z" -> Scissors
    else -> throw IllegalArgumentException("Invalid shape: $this")
}

fun Shape.fight(other: Shape): Int = when {
    this == other -> score + Draw.score
    this is Rock && other is Scissors -> score + Win.score
    this is Paper && other is Rock -> score + Win.score
    this is Scissors && other is Paper -> score + Win.score
    else -> score + Lose.score
}

fun File.part1() = readLines()
    .map { it.split("\\s+".toRegex()).map(String::asShape) }
    .sumOf { (you, opponent) -> you.fight(opponent) }
    .also(::println)

fun File.part2() = readLines()

fun main() {
    with(loadInput(day = 2)) {
        part1()
        part2()
    }
}