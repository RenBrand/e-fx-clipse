package at.bestsolution.efxclipse.runtime.examples.xtend

import java.io.PrintStream
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.layout.HBox
import javafx.stage.Stage

import static javafx.application.Application.*

class MyBuilderApplication extends Application {
	private extension FXBuilder b = new FXBuilder
	private extension PrintStream out = System::out

	override start(Stage primaryStage) throws Exception {
		val p = FxBean(null, typeof(HBox)) [
			children += FxBean(typeof(TextField)) [
				id = "helloField"
			]
			children += FxBean(typeof(Button))[
				text = "Say hello"
				onAction = [
					val f = primaryStage.scene.root.lookup("#helloField") as TextField
					f.text.println
				]
				
			]
		]
		
		primaryStage.scene = new Scene(p)
		primaryStage.width = 300
		primaryStage.show
	}
	
	def static void main(String[] args) {
		launch(args)
	}
	
}