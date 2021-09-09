package view

import controller.CircularPlacementStrategy
import controller.Representation
import tornadofx.*
import controller.Centrality
import model.ImportFromListOfEdges
import model.Importer
import model.TwoDLayout
import model.Layout


class MainView: View("Graph visualizer") {
    private var graph = GraphView(props.SAMPLE_GRAPH)
    private val strategy: Representation by inject<CircularPlacementStrategy>()
    private val centrality: Representation by inject<Centrality>()
    private val layout: Layout by inject<TwoDLayout>()
    private val import: Importer by inject<ImportFromListOfEdges>()

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
            button("Save graph as a 2D layout") {
                action {
                    layout.export(graph)
                }
            }
            button("Import graph from a 2D layout") {
                action {
                    graph = layout.import()
                    center {
                        add(graph)
                    }
                }
            }
            button("Import graph from a list of edges") {
                action {
                    graph = import.read(",")
                    center {
                        add(graph)
                    }
                    arrangeVertices()
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
