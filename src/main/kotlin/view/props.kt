package view

import tornadofx.booleanProperty
import tornadofx.doubleProperty
import model.Graph

object props {
    object layout {
        val auto = booleanProperty()
    }

    object vertex {
        val radius = doubleProperty(10.0)
        val label = booleanProperty()
    }

    object edge {
        val label = booleanProperty()
    }

    val SAMPLE_GRAPH: Graph = Graph().apply {
        connect("A", "B", 1.0)
        connect("A", "C", 1.0)
        connect("A", "D", 1.0)
        connect("A", "E", 1.0)
        connect("A", "F", 1.0)
        connect("A", "G", 1.0)

        connect("H", "I", 1.0)
        connect("H", "J", 1.0)
        connect("H", "K", 1.0)
        connect("H", "L", 1.0)
        connect("H", "M", 1.0)
        connect("H", "N", 1.0)

        connect("A", "H", 1.0)
    }
}