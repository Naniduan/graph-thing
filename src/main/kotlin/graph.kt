open class Node{
    public var neighbors: Set<Node> = emptySet()
    public var color = "ffffff"
    public var x: Double = 0.0
    public var y: Double = 0.0
    public var size: Double = 1.0

    public fun adjust(node: Node){
        neighbors += node
        node.neighbors += this
    }
}

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

open class Graph{
    public var nodes: Set<Node> = emptySet()

    public fun addNode(){
        var node = Node()
        nodes += node
    }

    public fun deleteNode(node: Node){
        nodes -= node
        for (i in node.neighbors){
            i.neighbors -= node
        }
    }

    public fun layoutGraph(){TODO()}

    public fun findKeyNodes(){TODO()}

    public fun findCommunities(){TODO()}
}

// controller

// view