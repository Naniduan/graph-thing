package view

import controller.VertexDragController
import model.Graph
import model.Vertex
import model.Edge
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import tornadofx.add
import tornadofx.find

class GraphView(private val graph: Graph = Graph()): Pane() {
    private val dragger = find(VertexDragController::class)
    private val vertices by lazy {
        graph.vertices.associateWith {
            VertexView(it, 0.0, 0.0, props.vertex.radius, Color.BLACK) }
    }
    private val edges by lazy {
        graph.edges.associateWith {
            val first = vertices[it.vertex1]
                ?: throw IllegalStateException("VertexView for ${it.vertex1} not found")
            val second = vertices[it.vertex2]
                ?: throw IllegalStateException("VertexView for ${it.vertex2} not found")
            EdgeView(first, second)
        }
    }

    fun vertices(): Collection<VertexView> = vertices.values
    fun edges(): Collection<EdgeView> = edges.values

    init {
        vertices().forEach { v ->
            add(v)
            add(v.label)
            v.setOnMouseEntered { e -> e?.let { dragger.entered(it) }}
            v.setOnMousePressed { e -> e?.let { dragger.pressed(it) }}
            v.setOnMouseDragged { e -> e?.let { dragger.dragged(it) }}
            v.setOnMouseReleased { e -> e?.let { dragger.released(it) }}
            v.setOnMouseExited { e -> e?.let { dragger.exited(it) }}
        }
        edges().forEach {
            add(it)
        }
    }
}