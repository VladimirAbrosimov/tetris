package ru.nsu.vabrosimov.tetris.view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.nsu.vabrosimov.tetris.Config;
import ru.nsu.vabrosimov.tetris.controller.MainController;
import ru.nsu.vabrosimov.tetris.controller.MainControllerAction;

import java.util.ArrayList;

public class PauseMenuView {
    private int windowWidth;
    private int windowHeight;

    private Stage stage;
    private Scene pauseMenuScene;
    private Pane root;
    private VBox buttons;
    private Button buttonStart = new Button("New Game");
    private Button buttonExit = new Button("Exit");

    private MainController mainController;


    public PauseMenuView(MainController mainController) {
        this.mainController = mainController;
        this.stage = mainController.getStage();
        this.windowWidth = Config.GAME_FIELD_WIDTH * Config.CELL_SIZE;
        this.windowHeight = Config.GAME_FIELD_HEIGHT * Config.CELL_SIZE;
    }

    public void initWindow() {
        this.root = new StackPane();
        this.pauseMenuScene = new Scene(this.root, this.windowWidth, this.windowHeight);
        pauseMenuScene.getStylesheets().add("PauseMenuStyles.css");
        this.root.getStyleClass().add("root");

        this.buttons = new VBox();
        this.handleEvents();
        this.updateWindow();

        this.stage.setScene(this.pauseMenuScene);
        this.root.getChildren().add(this.buttons);
        this.stage.show();
    }

    public void updateWindow() {
        this.drawButtons();
    }


    private void handleEvents() {
        buttonStart.setOnAction(e -> {
            this.mainController.performAction(
                    MainControllerAction.START_GAME);
        });
        buttonExit.setOnAction(e -> {
            this.mainController.performAction(
                    MainControllerAction.CLOSE_WINDOW);
        });
        this.pauseMenuScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                var keyCode = e.getCode();
                if (keyCode == KeyCode.ESCAPE) {
                    mainController.performAction(
                            MainControllerAction.RESUME_GAME);
                }
            }
        });
    }

    private void drawButtons() {
        this.buttons.setSpacing(20);
        this.buttons.setAlignment(Pos.CENTER);

        ArrayList<Button> btns = new ArrayList<>();
        btns.add(this.buttonStart);
        btns.add(this.buttonExit);
        btns.forEach(btn -> {
            btn.getStyleClass().add("button");
            this.buttons.getChildren().add(btn);
        });
    }
}
