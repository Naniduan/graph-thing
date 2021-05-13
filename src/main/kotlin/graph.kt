class Node{
    public var vertexes: MutableSet<Vertex> = mutableSetOf()

    //public var color
    //public var x
    //public var y

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

        vertexes.add(vertex)
        sumOfWeights += vertex.weight
    }
}