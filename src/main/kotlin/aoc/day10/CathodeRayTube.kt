package aoc.day10

import aoc.loadInput
import java.io.File

/**
 * https://adventofcode.com/2022/day/10
 *
 * @author Dominik Hoftych
 */

sealed interface Instruction
data class Addx(val x: Int) : Instruction
object Noop : Instruction

fun String.asInstruction(): List<Instruction> = when {
    matches("addx [-\\d]+".toRegex()) -> listOf(Noop, split(" ").last().toInt().let(::Addx))
    this == "noop" -> listOf(Noop)
    else -> throw IllegalArgumentException("Invalid instruction: $this")
}

data class Register(val cycle: Int = 1, val x: Int = 1)
infix fun Register.execute(instruction: Instruction): Register = when (instruction) {
    is Addx -> copy(cycle = cycle + 1, x = x + instruction.x)
    Noop -> copy(cycle = cycle + 1)
}

fun File.part1() = readLines()
    .flatMap(String::asInstruction)
    .runningFold(Register(), Register::execute)
    .filter { it.cycle in listOf(20, 60, 100, 140, 180, 220) }
    .sumOf { it.cycle * it.x }
    .also(::println)

@JvmInline
value class CRT(val pixels: String = "") {
    val position: Int get() = pixels.length % 40
}
infix fun CRT.draw(pixel: String) = CRT("${pixels}$pixel")

data class RegisterWithCRT(val register: Register = Register(), val crt: CRT = CRT())
val Register.sprite: IntRange get() = x - 1 .. x + 1

infix fun RegisterWithCRT.execute(instruction: Instruction): RegisterWithCRT =
    copy(
        register = register execute instruction,
        crt = if (crt.position in register.sprite)
            crt draw "#"
        else
            crt draw "."
    )

fun File.part2() = readLines()
    .flatMap(String::asInstruction)
    .fold(RegisterWithCRT(), RegisterWithCRT::execute)
    .crt
    .pixels
    .chunked(40)
    .joinToString("\n")
    .also(::println)

fun main() {
    with(loadInput(day = 10)) {
        part1()
        part2()
    }
}