//import tornadofx.*
import java.awt.Color

class Node{
    public var vertexes: MutableSet<Vertex> = mutableSetOf()
    public var vertexFromNode: MutableMap<Node,Vertex> = mutableMapOf()

    public var color = Color.WHITE
    public var x: Double = 0.0
    public var y: Double = 0.0

    public fun neighbours(): Set<Node>{
        val neighbours: MutableSet<Node> = mutableSetOf()
        for (vertex in this.vertexes){
            for (node in vertex.nodes){
                if (node!=this) neighbours.add(node)
            }
        }
        return neighbours
    }

    public fun sumOfWeights(): Double{ //k i
        var sum: Double = 0.0
        for (vertex in this.vertexes){
            sum+=vertex.weight
        }
        return sum
    }
}

class Vertex(node1: Node, node2: Node, public var weight: Double){
    public var nodes = setOf(node1, node2)
}

class Graph{
    public var nodes: MutableSet<Node> = mutableSetOf()
    public var vertexes: MutableSet<Vertex> = mutableSetOf()
    public var sumOfWeights: Double = 0.0 // m

    public fun connect(node1: Node, node2: Node, weight: Double){
        nodes.add(node1)
        nodes.add(node2)

        val vertex = Vertex(node1,node2,weight)
        node1.vertexes.add(vertex)
        node2.vertexes.add(vertex)
        node1.vertexFromNode[node2] = vertex
        node2.vertexFromNode[node1] = vertex

        vertexes.add(vertex)
        sumOfWeights += vertex.weight
    }
}