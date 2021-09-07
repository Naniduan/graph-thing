import logger.log
import styles.Styles
import view.MainView
import javafx.stage.Stage
import tornadofx.App
import tornadofx.launch

class MainApp: App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        log("Starting the application...")

        with(stage) {
            width = 800.0
            height = 600.0
        }
        super.start(stage)

        log("The application has started")
    }
}

fun main(args: Array<String>) {
    launch<MainApp>(args)
}