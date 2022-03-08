package ru.nsu.vabrosimov.tetris.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import ru.nsu.vabrosimov.tetris.Config;
import ru.nsu.vabrosimov.tetris.model.GameAction;
import ru.nsu.vabrosimov.tetris.model.GameField;
import ru.nsu.vabrosimov.tetris.view.GameView;

public class GameController {
    private MainController mainController;
    private GameField gameField;
    private GameView gameView;

    private Timeline gameTimer;

    public GameController(MainController mainController) {
        this.mainController = mainController;
        this.gameField = new GameField();
        this.gameView = new GameView(this.mainController, this.gameField);
        this.gameField.setGameView(this.gameView);
    }

    public void startWindow() {
        this.gameView.initWindow();
        this.initGameTimer();
    }

    public void resumeWindow() {
        this.gameView.initWindow();
        this.gameTimer.play();
    }

    public void pauseWindow() {
        this.gameTimer.pause();
    }

    public void closeWindow() {
        this.gameTimer.stop();
    }

    private void initGameTimer() {
        this.gameTimer = new Timeline(new KeyFrame(Duration.millis(Config.TIME_UPDATE), ev -> {
            this.gameNextStep();
            this.gameView.updateWindow();
        }));
        this.gameTimer.setCycleCount(Animation.INDEFINITE);
        this.gameTimer.play();
    }

    public void gameNextStep() {
        if (this.gameField.isLose()) {
            this.mainController.performAction(MainControllerAction.CLOSE_GAME);
            this.mainController.performAction(MainControllerAction.START_PAUSE_MENU);
        }
        this.gameField.nextStep();
    }

    public void gamePerformAction(GameAction gameAction) {
        this.gameField.performAction(gameAction);
    }

}
