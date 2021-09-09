package model

import tornadofx.Controller
import view.GraphView
import java.io.File
import java.io.InputStream

interface Importer{
    fun read(divider: String): GraphView
}

class ImportFromListOfEdges: Controller(), Importer {
    override fun read(divider: String):GraphView {
        val inputStream: InputStream = File("src/main/resources/ListOfEdges.txt").inputStream()
        val lineList = mutableListOf<String>()
        val graph = Graph()

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it) } }

        lineList.forEach {
            val (first, second) = it.split(divider)
            graph.connect(first, second)
        }
        return GraphView(graph)
    }
}