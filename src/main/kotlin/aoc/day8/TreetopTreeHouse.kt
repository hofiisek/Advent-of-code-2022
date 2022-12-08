package aoc.day8

import aoc.Matrix
import aoc.Position
import aoc.loadInput
import aoc.nodesDown
import aoc.nodesToTheLeft
import aoc.nodesToTheRight
import aoc.nodesUp
import java.io.File

/**
 * https://adventofcode.com/2022/day/8
 *
 * @author Dominik Hoftych
 */

typealias Forest = Matrix<Tree>

data class Tree(val height: Int, val pos: Position)

fun File.loadForest() = readLines()
    .map { it.split("").filterNot(String::isBlank).map(String::toInt) }
    .mapIndexed { rowIdx, row ->
        row.mapIndexed { colIdx, height -> Tree(height, Position(rowIdx, colIdx)) }
    }.let(::Matrix)

infix fun List<Tree>.anyTallerThan(tree: Tree): Boolean = none { it.height >= tree.height }

infix fun List<Tree>.numShorterTreesThan(tree: Tree): Int =
    when (val idx = indexOfFirst { it.height >= tree.height }) {
        0 -> 1
        -1 -> size
        else -> idx + 1
    }

context(Forest)
fun Tree.isVisibleFromOutside(): Boolean = nodesToTheLeft(pos) anyTallerThan this
        || nodesToTheRight(pos) anyTallerThan this
        || nodesUp(pos) anyTallerThan this
        || nodesDown(pos) anyTallerThan this

context(Forest)
fun Tree.viewingDistance(): Int {
    val shorterToLeft = nodesToTheLeft(pos) numShorterTreesThan this
    val shorterToRight = nodesToTheRight(pos) numShorterTreesThan this
    val shorterToUp = nodesUp(pos) numShorterTreesThan this
    val shorterToDown = nodesDown(pos) numShorterTreesThan this
    return shorterToLeft * shorterToRight * shorterToUp * shorterToDown
}

fun File.part1() = with(loadForest()) {
    flatten().count { tree -> tree.isVisibleFromOutside() }
}.also(::println)

fun File.part2() = with(loadForest()) {
    flatten().map { tree -> tree.viewingDistance() }.maxBy { it }
}.also(::println)

fun main() {
    with(loadInput(day = 8)) {
        part1()
        part2()
    }
}
