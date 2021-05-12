// it doesn't override Graph because Community.addNode takes a node,
// which Graph.addNode doesn't. Graph doesn't override Community
// because that would be nonsense

class Comunity(){
    public var nodes: Set<Node> = emptySet()

    public fun addNode(node: Node){
        nodes += node
    }

    public fun deleteNode(node: Node){
        nodes -= node
    }
}

fun louvain(graph: Graph){
    TODO()
}