// it doesn't override Graph because Community.addNode takes a node,
// which Graph.addNode doesn't. Graph doesn't override Community
// because that would be nonsense

class Community(node: Node){
    public var nodes: MutableSet<Node> = mutableSetOf(node)
    public var sumOfAllWeights: Double = 0.0

    public fun sumOfNeighbouringWeights(node: Node): Double{
        if (nodes.size <= 1) return 0.0

        var theSum: Double = 0.0

        for (vertex in node.vertexes){
            for (i in vertex.nodes){
                if (i!=node && i in nodes) theSum += vertex.weight
            }
        }

        return theSum
    }

    public fun addNode(node: Node){
        nodes.add(node)
        sumOfAllWeights += sumOfNeighbouringWeights(node)
    }

    public fun deleteNode(node: Node){
        nodes.remove(node)
        sumOfAllWeights -= sumOfNeighbouringWeights(node)
    }
}

fun sumOfWeights(vertexes: MutableSet<Vertex>): Double{
    var theSum: Double = 0.0

    for (vertex in vertexes) theSum+=vertex.weight

    return theSum
}

fun louvain(graph: Graph){
    var communities: MutableSet<Community> = mutableSetOf()
    val sumOfWeightsInGraph: Double = sumOfWeights(graph.vertexes)

    for (i in graph.nodes){
        communities.add(Community(i))
    }

    var modularity: Double = 0.0

    fun firstPhase(){
        
    }
}