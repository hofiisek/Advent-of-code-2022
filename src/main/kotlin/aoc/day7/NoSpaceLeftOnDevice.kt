package aoc.day7

import aoc.dropFirst
import aoc.loadInput
import java.io.File

/**
 * https://adventofcode.com/2022/day/8
 *
 * @author Dominik Hoftych
 */

val RGX_FILE = "(\\d+) ([\\w.]+)".toRegex()
val RGX_DIR = "dir (\\w+)".toRegex()
val RGX_CD_DIR = "\\$ cd (\\w+)".toRegex()
val RGX_CD_BACK = "\\$ cd ..".toRegex()
val RGX_LS = "\\$ ls".toRegex()

sealed interface Command
object Ls : Command
object CdBack : Command
object Dir : Command
data class CdDir(val into: String): Command
data class AocFile(val name: String, val size: Int): Command

data class Directory(
    val name: String,
    val parent: Directory? = null,
    val directories: MutableList<Directory> = mutableListOf(),
    val files: MutableList<AocFile> = mutableListOf()
)
fun Directory.size(): Int = files.sumOf(AocFile::size) + directories.sumOf(Directory::size)

fun String.asCommand(): Command = when {
    matches(RGX_FILE) -> RGX_FILE.matchEntire(this)!!
        .groupValues
        .let {
            val size = it[1]
            val name = it[2]
            AocFile(name, size.toInt())
        }
    matches(RGX_CD_DIR) -> RGX_CD_DIR.matchEntire(this)!!
        .groupValues
        .last()
        .let(::CdDir)
    matches(RGX_CD_BACK) -> CdBack
    matches(RGX_DIR) -> Dir
    matches(RGX_LS) -> Ls
    else -> throw IllegalArgumentException("Invalid command: $this")
}

fun List<Command>.readDirectories(): List<Directory> {
    fun readDirectories(
        currentDir: Directory,
        dirs: List<Directory> = emptyList(),
        commands: List<Command> = this
    ): List<Directory> = when (val command = commands.firstOrNull()) {
        null -> dirs // no more commands
        is AocFile -> readDirectories(currentDir.also { it.files.add(command) }, dirs, commands.dropFirst())
        CdBack -> readDirectories(
            currentDir.parent ?: throw IllegalArgumentException("Dir $currentDir has no parent"),
            dirs,
            commands.dropFirst()
        )
        is CdDir -> {
            val newDir = Directory(command.into, currentDir)
            readDirectories(newDir.also { currentDir.directories.add(it) }, dirs + newDir, commands.dropFirst())
        }
        Ls, Dir -> readDirectories(currentDir, dirs, commands.dropFirst())
    }

    val rootDir = Directory("/")
    return readDirectories(rootDir, listOf(rootDir))
}

fun File.part1() = readLines()
    .dropFirst()
    .map(String::asCommand)
    .let(List<Command>::readDirectories)
    .map(Directory::size)
    .filter { it < 100000 }
    .sum()
    .also(::println)

fun File.part2() = readLines()
    .dropFirst()
    .map(String::asCommand)
    .let(List<Command>::readDirectories)
    .let { directories ->
        val freeSpace = 70000000 - directories.first().size()
        directories.map { it.size() }
            .filter { size -> freeSpace + size >= 30000000 }
            .minBy { it }
    }
    .also(::println)


fun main() {
    with(loadInput(day = 7)) {
        part1()
        part2()
    }
}

