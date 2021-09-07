package controller

import view.VertexView

interface Representation {
    fun place(width: Double, height: Double, vertices: Collection<VertexView>)

    fun highlight(vertices: Collection<VertexView>)
}
