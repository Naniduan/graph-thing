package model

import javafx.beans.property.DoubleProperty
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import tornadofx.text

class Vertex(val name: String){
    public var edges: MutableSet<Edge> = mutableSetOf()
    public var edgeFromVertex: MutableMap<Vertex,Edge> = mutableMapOf()
    var centrality: Double = 1.0

    public fun neighbours(): Set<Vertex>{
        val neighbours: MutableSet<Vertex> = mutableSetOf()
        for (Edge in this.edges){
            for (Vertex in Edge.vertices){
                if (Vertex!=this) neighbours.add(Vertex)
            }
        }
        return neighbours
    }

    public fun sumOfWeights(): Double{ //k i
        var sum: Double = 0.0
        for (Edge in this.edges){
            sum+=Edge.weight
        }
        return sum
    }
}

class Edge(val vertex1: Vertex, val vertex2: Vertex, public var weight: Double){
    public var vertices = setOf(vertex1, vertex2)
}

class Graph{
    public var vertices: MutableSet<Vertex> = mutableSetOf()
    var verticesFromLabels: MutableMap<String, Vertex> = mutableMapOf()
    public var edges: MutableSet<Edge> = mutableSetOf()
    public var sumOfWeights: Double = 0.0 // m

    public fun connect(label1: String, label2: String, weight: Double = 1.0){
        var vertex1: Vertex
        var vertex2: Vertex

        if (verticesFromLabels[label1] == null){
            vertex1 = Vertex(label1)
            vertices.add(vertex1)
            verticesFromLabels[label1] = vertex1
        }
        else{
            vertex1 = verticesFromLabels[label1]!!
        }

        if (verticesFromLabels[label2] == null){
            vertex2 = Vertex(label2)
            vertices.add(vertex2)
            verticesFromLabels[label2] = vertex2
        }
        else{
            vertex2 = verticesFromLabels[label2]!!
        }

        val Edge = Edge(vertex1,vertex2,weight)
        vertex1.edges.add(Edge)
        vertex2.edges.add(Edge)
        vertex1.edgeFromVertex[vertex2] = Edge
        vertex2.edgeFromVertex[vertex1] = Edge

        edges.add(Edge)
        sumOfWeights += Edge.weight
    }


}