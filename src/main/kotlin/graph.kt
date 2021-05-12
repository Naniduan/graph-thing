import java.util.Random
val random = Random()
fun rand(from: Double, to: Double) : Double { return random.nextInt((to*1000).toInt()-(from*1000).toInt())/1000.0 + from }

val graphSize: Double = 1000.0

open class Node{
    public var neighbors: Set<Node> = emptySet()
    public var color = "ffffff"
    public var x: Double = rand(-graphSize,graphSize)
    public var y: Double = rand(-graphSize,graphSize)
    public var size: Double = 1.0

    public fun adjust(node: Node){
        neighbors += node
        node.neighbors += this
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

    //public fun findKeyNodes(){TODO()}

    public fun findCommunities(){
        louvain(this)
    }
}