import javax.swing.MutableComboBoxModel
import kotlin.math.pow

class Community(node: Node){
    public var nodes: MutableSet<Node> = mutableSetOf(node)
    public var sumOfAllWeights: Double = 0.0 //Sum in
    public var sumOfEnteringVertexes: Double = node.sumOfWeights() //Sum tot

    public fun sumOfWeightsInCommunity(node: Node): Double{ //k i, in
        if (nodes.size <= 1) return 0.0

        var theSum: Double = 0.0

        for (vertex in node.vertexes){
            for (i in vertex.nodes){
                if (i!=node && i in nodes) theSum += vertex.weight
            }
        }

        return theSum
    }

    public fun sumOfWeightsOutsideCommunity(node: Node): Double{
        if (nodes.size <= 1) return 0.0

        var theSum: Double = 0.0

        for (vertex in node.vertexes){
            for (i in vertex.nodes){
                if (i!=node && i !in nodes) theSum += vertex.weight
            }
        }

        return theSum
    }

    public fun addNode(node: Node){
        nodes.add(node)
        sumOfAllWeights += sumOfWeightsInCommunity(node)
        sumOfEnteringVertexes -= sumOfWeightsInCommunity(node)
        sumOfEnteringVertexes += sumOfWeightsOutsideCommunity(node)
    }

    public fun deleteNode(node: Node){
        nodes.remove(node)
        sumOfAllWeights -= sumOfWeightsInCommunity(node)
        sumOfEnteringVertexes += sumOfWeightsInCommunity(node)
        sumOfEnteringVertexes -= sumOfWeightsOutsideCommunity(node)
    }
}

fun sumOfWeights(vertexes: MutableSet<Vertex>): Double{
    var theSum: Double = 0.0

    for (vertex in vertexes) theSum+=vertex.weight

    return theSum
}

class Louvain{
    var communities: MutableSet<Community> = mutableSetOf()
    var communityOfTheNode: MutableMap<Node, Community> = mutableMapOf()

    var modularity: Double = 0.0

    private fun changeInModularity(community: Community, node: Node, graph: Graph): Double{
        var firstPart = (community.sumOfAllWeights + 2*community.sumOfWeightsInCommunity(node))/(2*graph.sumOfWeights)
        firstPart -= ((community.sumOfEnteringVertexes + node.sumOfWeights())/(2*graph.sumOfWeights)).pow(2)

        var secondPart = community.sumOfAllWeights/(2*graph.sumOfWeights)
        secondPart -= (community.sumOfEnteringVertexes/(2*graph.sumOfWeights)).pow(2)
        secondPart -= (node.sumOfWeights()/(2*graph.sumOfWeights)).pow(2)

        return firstPart - secondPart
    }

    fun firstPhase(graph: Graph){
        for (i in graph.nodes){
            val community = Community(i)
            communities.add(community)
            communityOfTheNode[i] = community
        }

        var optimizeable: Boolean = true
        while(optimizeable) {
            optimizeable = false
            for (node in graph.nodes) {
                var optimalCommunity = communityOfTheNode[node]
                var maxChangeInModularity: Double = -1.0
                for (neighbour in node.neighbours()) {
                    val tryThisCommunity: Community = communityOfTheNode[neighbour]!!
                    if (maxChangeInModularity < changeInModularity(tryThisCommunity, node, graph)) {
                        maxChangeInModularity = changeInModularity(tryThisCommunity, node, graph)
                        optimalCommunity = tryThisCommunity
                    }
                }

                if (maxChangeInModularity > 0) {
                    optimizeable = true

                    communityOfTheNode[node]!!.deleteNode(node)
                    optimalCommunity!!.addNode(node)
                    if (communityOfTheNode[node]!!.nodes.size == 0) communities.remove(communityOfTheNode[node])

                    communityOfTheNode[node] = optimalCommunity

                    modularity += maxChangeInModularity
                }
            }
        }
    }

    fun secondPhase(graph: Graph){
        val secondPhaseGraph = Graph()
        val secondPhaseNodes: MutableMap<Community,Node> = mutableMapOf()
        for (community in communities){
            secondPhaseNodes[community] = Node()
        }

        for (vertex in graph.vertexes){
            val ends: Array<Node> = Array(2,{i -> Node()})
            var i = 0
            for (node in vertex.nodes){
                ends[i] = node
                i+=1
            }

            if (secondPhaseNodes[communityOfTheNode[ends[0]]]!!.
                vertexFromNode[secondPhaseNodes[communityOfTheNode[ends[1]]]] == null)
                {
                secondPhaseGraph.connect(secondPhaseNodes[communityOfTheNode[ends[0]]]!!,
                    secondPhaseNodes[communityOfTheNode[ends[1]]]!!,vertex.weight)
            }

            else
            {
                secondPhaseNodes[communityOfTheNode[ends[0]]]!!.
                    vertexFromNode[secondPhaseNodes[communityOfTheNode[ends[1]]]]!!.weight +=
                    vertex.weight
            } // the ?:run{} construction doesn't work
        }

        val firstPhaseCommunities = communities
        val firstPhaseCommunityOfTheNode = communityOfTheNode

        communities = mutableSetOf()
        communityOfTheNode = mutableMapOf()

        firstPhase(secondPhaseGraph)

        // now reassign communities

        for (node in graph.nodes){
            communityOfTheNode[secondPhaseNodes[firstPhaseCommunityOfTheNode[node]]]!!.addNode(node)
            communityOfTheNode[node] = communityOfTheNode[secondPhaseNodes[firstPhaseCommunityOfTheNode[node]]]!!
        }

        for (node in secondPhaseGraph.nodes){
            communityOfTheNode[secondPhaseNodes[firstPhaseCommunityOfTheNode[node]]]!!.deleteNode(node)
        }
    }
}