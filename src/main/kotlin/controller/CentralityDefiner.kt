package controller

import javafx.scene.paint.Color
import tornadofx.Controller
import view.VertexView
import kotlin.math.round

class Centrality: Controller(), Representation {
    private fun color(vertex: VertexView, maxCentrality: Double, minCentrality: Double){
        var difference: Int = 255- round(((vertex.vertex.centrality-minCentrality)/(maxCentrality-minCentrality)) *255).toInt()
        //if the centrality is high, the node is red, otherwise, it is more white
        var newColor = "#ff"+difference.toString(16)+difference.toString(16)
        vertex.color = Color.web(newColor)
    }

    override fun highlight(vertices: Collection<VertexView>){
        var newCentralities: MutableMap<VertexView, Double> = mutableMapOf()
        var maxCentrality = 0.0
        var minCentrality = -1.0
        for (vertex in vertices){
            newCentralities[vertex] = vertex.vertex.centrality
            for (neighbour in vertex.vertex.neighbours()){
                if (newCentralities[vertex]!=null){
                    newCentralities[vertex]=newCentralities[vertex]!!+neighbour.centrality
                }
            }

            if (newCentralities[vertex]!! > maxCentrality){
                maxCentrality = newCentralities[vertex]!!
            }

            if (minCentrality==-1.0||newCentralities[vertex]!! < minCentrality){
                minCentrality = newCentralities[vertex]!!
            }
        }

        for (vertex in newCentralities){
            vertex.key.vertex.centrality = vertex.value
            color(vertex.key, maxCentrality, minCentrality)
        }
    }

    override fun place(width: Double, height: Double, vertices: Collection<VertexView>) {
    }
}