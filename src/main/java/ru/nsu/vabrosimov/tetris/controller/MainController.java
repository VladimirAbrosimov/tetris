package ru.nsu.vabrosimov.tetris.controller;

import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MainController {
    Stage primaryStage;
    private GameController gameController;
    private PauseMenuController pauseMenuController;

    private Map<MainControllerAction, Runnable> mainControllerActionRunnableMap;

    public MainController() {
        this.primaryStage = new Stage();
        this.primaryStage.setTitle("Tetris");
        this.primaryStage.setResizable(false);
        this.pauseMenuController = new PauseMenuController(this);
        this.initMainControllerActionRunnableMap();
    }

    public void start() {
        this.performAction(MainControllerAction.START_PAUSE_MENU);
    }

    public void performAction(MainControllerAction mainControllerAction) {
        if (this.mainControllerActionRunnableMap.containsKey(mainControllerAction)) {
            this.mainControllerActionRunnableMap.get(mainControllerAction).run();
        }
    }

    private void initMainControllerActionRunnableMap() {
        this.mainControllerActionRunnableMap = new HashMap<>();
        this.mainControllerActionRunnableMap.put(
                MainControllerAction.CLOSE_WINDOW, () -> this.closeWindow()
        );
        this.mainControllerActionRunnableMap.put(
                MainControllerAction.START_GAME, () -> this.startGame()
        );
        this.mainControllerActionRunnableMap.put(
                MainControllerAction.CLOSE_GAME, () -> this.closeGame()
        );
        this.mainControllerActionRunnableMap.put(
                MainControllerAction.PAUSE_GAME, () -> this.pauseGame()
        );
        this.mainControllerActionRunnableMap.put(
                MainControllerAction.RESUME_GAME, () -> this.resumeGame()
        );
        this.mainControllerActionRunnableMap.put(
                MainControllerAction.START_PAUSE_MENU, () -> this.startPauseMenu()
        );
    }

    public Stage getStage() {
        return this.primaryStage;
    }

    public GameController getGameController() {
        return this.gameController;
    }

    private void startGame() {
        this.gameController = new GameController(this);
        this.gameController.startWindow();
    }

    private void closeGame() {
        this.gameController.closeWindow();
    }

    private void pauseGame() {
        this.gameController.pauseWindow();
    }

    private void resumeGame() {
        this.gameController.resumeWindow();
    }

    private void startPauseMenu() {
        this.pauseMenuController.startWindow();
    }

    private void closeWindow() {
        this.primaryStage.close();
    }
}