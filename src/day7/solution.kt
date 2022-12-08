package day7

import java.io.File
import java.io.InputStream

enum class Type { file, dir }

data class Node(
    val name: String,
    val type: Type,
    var size: Int,
    val parent: Node?,
    val children: MutableMap<String, Node> = mutableMapOf()
)

fun recalc_size(v: Node): Int {
    if (v.children.isEmpty()) return v.size
    v.size = 0
    for (elem in v.children.values)
        v.size += recalc_size(elem)
    return v.size
}

fun cd(v: Node, dest: String, root: Node): Node {
    var curNode: Node = v
    if (dest.isEmpty()) return curNode
    if (dest == "..") return curNode.parent ?: root

    if (dest[0] == '/') return root

    val path = dest.let { if (dest[0] == '/') dest.substring(1) else dest }.split('/')
    for (elem in path) {
        curNode = curNode.children[elem]!!
    }

    return curNode
}

fun ls(i_start: Int, input: List<String>, curNode: Node): Int {
    var i = i_start
    while (i < input.size && input[i][0] != '$') {
        val line = input[i].split(' ')

        val name = line[1]
        if (curNode.children.contains(name)) {
            i++
            continue
        }

        val size = if (line[0] == "dir") 0 else line[0].toInt()
        val type = if (line[0] == "dir") Type.dir else Type.file
        curNode.children[name] = Node(name, type, size, curNode)

        i++
    }

    return i
}

fun get_filesize_sum(curNode: Node): Int {
    var answer = 0
    if (curNode.size <= 100000) answer += curNode.size
    for (elem in curNode.children.values)
        if (elem.type == Type.dir)
             answer += get_filesize_sum(elem)
    return answer
}

fun find_directories(curNode: Node, free_space: Int): List<Int> {
    val answer = mutableListOf<Int>()
    if (curNode.size+ free_space >= 30000000) answer += listOf(curNode.size)
    for (elem in curNode.children.values)
        if (elem.type == Type.dir)
            answer += find_directories(elem, free_space)
    return answer
}

fun init() {

    val inputStream: InputStream = File("src/day7/input.txt").inputStream()
    val input = inputStream.bufferedReader().use { it.readText() }.split('\n')

    val root = Node("/", Type.dir, 0, null)
    var curNode = root

    var i = 0
    while (i < input.size) {
        val tmp = input[i].split(' ')
        if (tmp[0] == "$")
            when (tmp[1]) {
                "cd" -> {
                    curNode = cd(curNode, tmp[2], root)
                    i++
                }
                "ls" -> {
                    i = ls(i + 1, input, curNode)
                }
            }
    }

    recalc_size(root)

    println(get_filesize_sum(root))

    println(find_directories(root, 70000000 - root.size).min())
}