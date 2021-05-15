import javafx.scene.paint.Color
import tornadofx.*

class MyView : View() {

    override val root = vbox {
        button("Press Me") {
            textFill = Color.RED
            action { println("Button pressed!") }
        }
    }
}