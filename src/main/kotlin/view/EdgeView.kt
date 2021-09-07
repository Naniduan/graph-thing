package view

import javafx.scene.shape.Line

class EdgeView(val first: VertexView, val second: VertexView): Line() {

    init {
        startXProperty().bind(first.centerXProperty())
        startYProperty().bind(first.centerYProperty())
        endXProperty().bind(second.centerXProperty())
        endYProperty().bind(second.centerYProperty())
    }

}