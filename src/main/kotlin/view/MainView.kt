package view

import controller.CircularPlacementStrategy
import controller.Representation
import tornadofx.*
import controller.Centrality


class MainView: View("Graph visualizer") {
    private val graph = GraphView(props.SAMPLE_GRAPH)
    private val strategy: Representation by inject<CircularPlacementStrategy>()
    private val centrality: Representation by inject<Centrality>()

    override val root = borderpane {
        center {
            add(graph)
        }
        left = vbox(10) {
            checkbox("Show vertices labels", props.vertex.label) {
                action {
                    println("vertex labels are ${if (isSelected) "enabled" else "disabled"}")
                }
            }
            button("Reset default settings") {
                action {
                    arrangeVertices()
                }
            }
            button("Iterate eigenvector centrality") {
                action {
                    findCentrality()
                }
            }
        }
    }

    init {
        arrangeVertices()
    }

    private fun arrangeVertices() {
        currentStage?.apply {
            strategy.place(
                width - props.vertex.radius.get() * 5,
                height - props.vertex.radius.get() * 5,
                graph.vertices(),
            )
        }
    }

    private fun findCentrality() {
        currentStage?.apply {
            centrality.highlight(graph.vertices())
        }
    }
}
