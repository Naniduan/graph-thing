package model

import javafx.scene.paint.Color
import tornadofx.Controller
import tornadofx.getValue
import tornadofx.toProperty
import view.GraphView
import view.VertexView
import view.EdgeView
import view.props
import java.io.File
import java.io.InputStream

interface Layout{
    fun export(graph: GraphView)
    fun import():GraphView
}

class TwoDLayout: Controller(), Layout {
    override fun export(graph: GraphView) {
        val fileName = "src/main/resources/2DLayout.txt"
        val file = File(fileName)
        file.writeText("${graph.edges().size} ${graph.vertices().size} ${props.vertex.radius.value}\n")


        for (edge in graph.edges()) {
            file.appendText("${edge.first.vertex.name} ${edge.second.vertex.name}\n")
            //println("${edge.first.vertex.name} ${edge.second.vertex.name}")
        }
        for (vertex in graph.vertices()) {
            file.appendText("${vertex.vertex.name} ${vertex.position.first} ${vertex.position.second} ${vertex.color}\n")
            //println("${vertex.vertex.name} ${vertex.x} ${vertex.y} ${vertex.color}")
        }
        //file.
    }

    override fun import():GraphView {
        val inputStream: InputStream = File("src/main/resources/2DLayout.txt").inputStream()
        val lineList = mutableListOf<String>()
        val graph = Graph()
        var graphView = GraphView(graph)

        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it)} }
        var amountOfEdges = 0
        var amountOfVertices = 0
        for (i in 0..lineList.size-1){
            if (i==0){
                val args = lineList[i].split(' ')
                amountOfEdges = args[0].toInt()
                amountOfVertices = args[1].toInt()
                props.vertex.radius = args[2].toDouble().toProperty()
            }else if (i<=amountOfEdges){
                val (first,second) = lineList[i].split(' ')
                graph.connect(first, second)
            }else{
                if (i == amountOfEdges+1){
                    graphView = GraphView(graph)
                }
                val args = lineList[i].split(' ')
                val name = args[0]
                val x = args[1].toDouble()
                val y = args[2].toDouble()
                val color = Color.web(args[3])

                graphView.vertices[graphView.vertexFromName[name]]?.color = color
                graphView.vertices[graphView.vertexFromName[name]]?.x = x
                graphView.vertices[graphView.vertexFromName[name]]?.y = y

                graphView.vertices[graphView.vertexFromName[name]]?.position = x to y
            }
        }
        return graphView
    }
}