package javafx.application.api;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UIHandler extends Application {

	/**
	 * @author Florian
	 */
	
	
	
	/**
	 * @param PATH_CLASS; Is used to find the FXML Paths starting from this Class.
	 */
	private static Class<?> PATH_CLASS;
	
	/**
	 * @param FXML_PATH; Is the Current Path from the Scene to the FXML File.
	 */
	private static String FXML_PATH = "";
			
	/**
	 * @param FAVICO_PATH; Is the Path to the displayed Favicon.
	 */
	private static String FAVICO_PATH = "";

	/**
	 * @param args; Are the arguments from the Main Method that are needed for the launch(); Method from Application.class.
	 */
	private static String[] args;

	/**
	 * @param APPLICATION_STARTED; is the boolean that saves the status of the Application launchables.
	 */
	private static boolean APPLICATION_STARTED = false;
	
	/**
	 * @param isUndecorated; Saves the Decoration status of the headbar from the Window.
	 */
	private static boolean isUndecorated;
	
	
	
	/**
	 * @param  args, PathClass, isUndecorated; Are used to initialize the Variables for the launch(); and start(); Method.
	 * @method initializeClass(); It initialize all Variables in the head part of this class.
	 */
	public static void initializeClass(String[] args, Class<?> PathClass, boolean isUndecorated) {
		UIHandler.args = args;
		UIHandler.PATH_CLASS = PathClass;
		UIHandler.isUndecorated = isUndecorated;
	}

	
	
	/**
	 * @param  IconPath; Is needed to get the Image File.
	 * @method addIcon(); Add the Favicon to the Window.
	 */
	public static void addIcon(String IconPath) {
		UIHandler.FAVICO_PATH = IconPath;
	}

	
	
	/**
	 * @param  FXMLPath, StageTitle; Are used to set the Window title and to find the FXML File that the method will display.
	 * @throws IOException
	 * @method setScene(); Set the new Scene in the open Widnow.
	 */
	public static void setScene(String FXMLPath, String StageTitle) throws IOException {
		UIHandler.FXML_PATH = FXMLPath;

		if (UIHandler.APPLICATION_STARTED) {
			UIHandler.FXML_PATH = FXMLPath;

			Parent Root = null;
			Root = FXMLLoader.load(PATH_CLASS.getResource(FXMLPath));

			if (isUndecorated) {
				Root.setOnMousePressed(MOVE_WINDOW_MOUSE_PRESSED);
				Root.setOnMouseDragged(MOVE_WINDOW_MOUSE_DRAG);
			}

			Scene Scene = new Scene(Root);

			UIHandler.primaryStage.setTitle(StageTitle);
			UIHandler.primaryStage.setScene(Scene);
			UIHandler.primaryStage.close();
			UIHandler.primaryStage.show();
		} else {
			UIHandler.APPLICATION_STARTED = true;
			UIHandler.StartStageTitle = StageTitle;
			launch(args);
		}
	}

	
	
	/**
	 * @param  FXMLPath, StageTitle; Are used to set the Window title and to find the FXML File that the method will display.
	 * @throws IOException
	 * @method setScene(); Set the new Scene smooth in the open Widnow beacause the Node is given.
	 */
	public static void setScene(String FXMLPath, Node rootPane, String StageTitle) throws IOException {
		UIHandler.FXML_PATH = FXMLPath;

		if (UIHandler.APPLICATION_STARTED) {
			UIHandler.FXML_PATH = FXMLPath;

			Parent newView = FXMLLoader.load(PATH_CLASS.getResource(FXMLPath));
			Scene newScene = new Scene(newView);
			Stage currentStage = (Stage) rootPane.getScene().getWindow();

			if (isUndecorated) {
				newView.setOnMousePressed(MOVE_WINDOW_MOUSE_PRESSED);
				newView.setOnMouseDragged(MOVE_WINDOW_MOUSE_DRAG);
			}
			currentStage.setTitle(StageTitle);
			currentStage.setScene(newScene);
		} else {
			UIHandler.APPLICATION_STARTED = true;
			UIHandler.StartStageTitle = StageTitle;
			launch(args);
		}
	}

	
	
	/**
	 * @param primaryStage; Saves the primaryStage from the start(): Method.
	 */
	private static Stage primaryStage;

	/**
	 * @param xOffset, yOffset; Saves the Offsets of the Window.
	 */
	private static double xOffset = 0, yOffset = 0;

	/**
	 * @param MOVE_WINDOW_MOUSE_PRESSED; Saves the MouseEvent for Pressing the Mouse to move the Window.
	 */
	private static EventHandler<MouseEvent> MOVE_WINDOW_MOUSE_PRESSED = new EventHandler<MouseEvent>(){@Override public void handle(MouseEvent event){UIHandler.xOffset=event.getSceneX();UIHandler.yOffset=event.getSceneY();}};

	/**
	 * @param MOVE_WINDOW_MOUSE_DRAG; Saves the MouseEvent for Dragging the Mouse to move the Window.
	 */
	private static EventHandler<MouseEvent> MOVE_WINDOW_MOUSE_DRAG = new EventHandler<MouseEvent>(){@Override public void handle(MouseEvent event){UIHandler.primaryStage.setX(event.getScreenX()-xOffset);UIHandler.primaryStage.setY(event.getScreenY()-yOffset);}};

	/**
	 * @param StartStageTitle; Saves the Title for the starting Method.
	 */
	private static String StartStageTitle;

	
	
	/**
	 * @param  primaryStage; Is given from tha Application.class for changing the Scenes on the Stage.
	 * @throws Exception
	 * @method start(); Is the start method from the Application.class it is needed to be in this class.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		UIHandler.primaryStage = primaryStage;

		Parent Root = FXMLLoader.load(PATH_CLASS.getResource(FXML_PATH));
		Scene Scene = new Scene(Root);

		if (isUndecorated) {
			UIHandler.primaryStage.initStyle(StageStyle.UNDECORATED);

			Root.setOnMousePressed(MOVE_WINDOW_MOUSE_PRESSED);
			Root.setOnMouseDragged(MOVE_WINDOW_MOUSE_DRAG);
		}

		if (FAVICO_PATH.length() > 0)
			UIHandler.primaryStage.getIcons().add(new Image(PATH_CLASS.getResourceAsStream(FAVICO_PATH)));

		primaryStage.setTitle(StartStageTitle);

		UIHandler.primaryStage.setScene(Scene);
		UIHandler.primaryStage.show();
	}

	
}
