package view

import javafx.beans.property.DoubleProperty
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import tornadofx.text
import model.Vertex
import tornadofx.toProperty

class VertexView (val vertex: Vertex,
                  var x: Double, var y: Double,
                  var rad: DoubleProperty,
                  color: Color)
    : Circle(x, y, rad.get(), color){

    init {
        radiusProperty().bind(rad)
    }

    var position: Pair<Double, Double>
        get() = centerX to centerY
        set(value) {
            centerX = value.first
            centerY = value.second
        }

    var color: Color
        get() = fill as Color
        set(value) {
            fill = value
        }

    val label = text(vertex.name.toString()) {
        visibleProperty().bind(props.vertex.label)
        xProperty().bind(centerXProperty().subtract(layoutBounds.width / 2))
        yProperty().bind(centerYProperty().add(radiusProperty()).add(layoutBounds.height))
    }
}