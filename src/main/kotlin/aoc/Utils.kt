package aoc

import java.lang.IllegalArgumentException

/**
 * @author Dominik Hoftych
 */

const val ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

fun String.splitInHalf(): Pair<Set<Char>, Set<Char>> = if (length % 2 == 0)
    take(length / 2).toSet() to takeLast(length / 2).toSet()
else
    throw IllegalArgumentException("String is of odd length")

fun Char.index(): Int = ALPHABET.indexOf(this)

fun String.distinctSet(): Set<Char> = toCharArray().toSet()

fun String.matchesNot(regex: Regex) = !matches(regex)

fun <T> Iterable<T>.dropFirst(): List<T> = drop(1)
