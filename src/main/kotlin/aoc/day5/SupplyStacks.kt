package aoc.day5

import aoc.loadInput
import aoc.matchesNot
import java.io.File

/**
 * https://adventofcode.com/2022/day/5
 *
 * @author Dominik Hoftych
 */

typealias Stacks = Map<Int, ArrayDeque<Crate>>

data class Crate(val stackId: Int, val id: String)
data class Move(val quantity: Int, val from: Int, val to: Int)

infix fun Stacks.perform(move: Move): Stacks {
    val from = getValue(move.from)
    val to = getValue(move.to)
    repeat(move.quantity) {
        from.removeFirst().also(to::addFirst)
    }

    return this
}

infix fun Stacks.performBulk(move: Move): Stacks {
    val from = getValue(move.from)
    val to = getValue(move.to)
    (0 until move.quantity)
        .map { from.removeFirst() }
        .reversed()
        .forEach(to::addFirst)

    return this
}

fun List<String>.readStacks(): Stacks = takeWhile { it.matchesNot("[\\s\\d]+".toRegex()) }
    .flatMap(String::readLineStacks)
    .groupBy(Crate::stackId)
    .mapValues { (_, crates) -> ArrayDeque(crates) }
    .toSortedMap()

fun String.readLineStacks(): List<Crate> = chunked(4)
    .map(String::trim)
    .mapIndexedNotNull { stackIdx, crate ->
        if (crate.isBlank())
            null
        else
            ".*([A-Z]).*".toRegex()
                .matchEntire(crate)
                ?.groupValues
                ?.last()
                ?.let { crateId -> Crate(stackIdx + 1, crateId) }
                ?: throw IllegalArgumentException("Illegal crate format: $crate")
    }

fun List<String>.readMoves(): List<Move> = filter { it.startsWith("move") }
    .map { it.replace("[move|from|to|]+".toRegex(), "").trim() }
    .map { it.split("\\s+".toRegex()).map(String::toInt) }
    .map { (quantity, from, to) -> Move(quantity, from, to) }

fun File.part1() = readLines()
    .let { lines ->
        lines.readMoves().fold(lines.readStacks()) { stacks, move -> stacks perform move }
    }
    .values
    .mapNotNull { it.firstOrNull()?.id }
    .joinToString("")
    .also(::println)

fun File.part2() = readLines()
    .let { lines ->
        lines.readMoves().fold(lines.readStacks()) { stacks, move -> stacks performBulk move }
    }
    .values
    .mapNotNull { it.firstOrNull()?.id }
    .joinToString("")
    .also(::println)

fun main() {
    with(loadInput(day = 5)) {
        part1()
        part2()
    }
}