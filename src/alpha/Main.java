/* ******************************************************
 * Simulator alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: alpha/Main.java 2015-03-09 buixuan.
 * ******************************************************/
package alpha;

import tools.HardCodedParameters;

import specifications.DataService;
import specifications.EngineService;
import specifications.ViewerService;
import specifications.AlgorithmService;

import data.Data;
import engine.Engine;
import userInterface.Viewer;
import algorithm.RobotIA;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.util.Duration;

public class Main extends Application{

	//---VARIABLES---//
	private static DataService data;
	private static EngineService engine;
	private static ViewerService viewer;
	private static AlgorithmService algorithm;

	private Timeline timeline;
	private AnimationTimer timer;

	//---EXECUTABLE---//
	public static void main(String[] args) {
		//readArguments(args);

		data = new Data();
		engine = new Engine();
		viewer = new Viewer();
		algorithm = new RobotIA();

		((Engine)engine).bindDataService(data);
		((Engine)engine).bindAlgorithmService(algorithm);
		((Viewer)viewer).bindReadService(data);
		((Viewer)viewer).bindStartEngineService(engine);
		((RobotIA)algorithm).bindSimulatorService(engine);

		engine.init();
		viewer.init();
		viewer.startViewer();

		launch(args);
	}

	@Override public void start(Stage stage) {
		Scene scene = new Scene(((Viewer)viewer).getPanel());
		stage.setScene(scene);
		stage.setWidth(HardCodedParameters.defaultWidth);
		stage.setHeight(HardCodedParameters.defaultHeight);
		stage.show();

		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);

		timer = new AnimationTimer() {
			@Override
			public void handle(long l) {
				if(l%25==0)
					scene.setRoot(((Viewer)viewer).getPanel());
			}
		};

		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(HardCodedParameters.enginePaceMillis)));
		timeline.play();
		timer.start();
	}
}
