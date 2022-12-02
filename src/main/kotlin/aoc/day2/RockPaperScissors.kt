package aoc.day2

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

fun String.asOutcome(): Outcome = when (this) {
    "X" -> Lose
    "Y" -> Draw
    "Z" -> Win
    else -> throw IllegalArgumentException("Invalid shape: $this")
}

infix fun Shape.fight(opponent: Shape): Int = when {
    this == opponent -> score + Draw.score
    this is Rock && opponent is Scissors -> score + Win.score
    this is Paper && opponent is Rock -> score + Win.score
    this is Scissors && opponent is Paper -> score + Win.score
    else -> score + Lose.score
}

infix fun Shape.yourShapeFor(outcome: Outcome): Shape = when {
    outcome is Draw -> this
    this is Paper && outcome == Win -> Scissors
    this is Paper && outcome == Lose -> Rock
    this is Rock && outcome == Win -> Paper
    this is Rock && outcome == Lose -> Scissors
    this is Scissors && outcome == Win -> Rock
    this is Scissors && outcome == Lose -> Paper
    else -> throw IllegalArgumentException("Invalid combination of shape $this and outcome $outcome")
}

fun File.part1() = readLines()
    .map { it.split(" ").map(String::asShape) }
    .sumOf { (opponent, you) -> you fight opponent }
    .also(::println)

fun File.part2() = readLines()
    .map { it.split(" ") }
    .map { (opponent, outcome) -> opponent.asShape() to outcome.asOutcome() }
    .sumOf { (opponent, outcome) -> outcome.score + (opponent yourShapeFor outcome).score }
    .also(::println)

fun main() {
    with(loadInput(day = 2)) {
        part1()
        part2()
    }
}