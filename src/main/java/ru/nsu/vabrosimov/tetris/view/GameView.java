package ru.nsu.vabrosimov.tetris.view;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.nsu.vabrosimov.tetris.Config;
import ru.nsu.vabrosimov.tetris.controller.MainController;
import ru.nsu.vabrosimov.tetris.controller.MainControllerAction;
import ru.nsu.vabrosimov.tetris.model.Block;
import ru.nsu.vabrosimov.tetris.model.BlockPoint;
import ru.nsu.vabrosimov.tetris.model.GameAction;
import ru.nsu.vabrosimov.tetris.model.GameField;

public class GameView {
    private int windowWidth;
    private int windowHeight;

    private Stage stage;
    private Scene gameScene;
    private Pane root;
    private Canvas canvas;
    private GraphicsContext gc;

    private GameField gameField;

    private MainController mainController;

    public GameView(MainController controller, GameField gameField) {
        this.mainController = controller;
        this.stage = mainController.getStage();
        this.gameField = gameField;
        this.windowWidth = Config.GAME_FIELD_WIDTH * Config.CELL_SIZE;
        this.windowHeight = Config.GAME_FIELD_HEIGHT * Config.CELL_SIZE;
    }

    public void initWindow() {
        this.root = new StackPane();
        this.gameScene = new Scene(this.root, this.windowWidth, this.windowHeight);
        this.canvas = new Canvas(this.windowWidth, this.windowHeight);
        this.gc = canvas.getGraphicsContext2D();
        this.handleEvents();
        this.updateWindow();
        this.stage.setScene(this.gameScene);
        this.root.getChildren().add(this.canvas);
        this.stage.show();
    }

    public void updateWindow() {
        this.gc.clearRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
        this.drawBackground();
        this.drawBlocks();
        this.drawFutureBlock();
        this.drawScore();
    }

    private void handleEvents() {
        this.gameScene.setOnKeyPressed(e -> {
            var keyCode = e.getCode();
            if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
                mainController.getGameController().gamePerformAction(GameAction.MOVE_LEFT);
            } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
                mainController.getGameController().gamePerformAction(GameAction.MOVE_RIGHT);
            } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.S) {
                mainController.getGameController().gamePerformAction(GameAction.DROP_DOWN);
            } else if (keyCode == KeyCode.UP || keyCode == KeyCode.W) {
                mainController.getGameController().gamePerformAction(GameAction.ROTATE);
            } else if (keyCode == KeyCode.ESCAPE) {
                mainController.performAction(MainControllerAction.PAUSE_GAME);
                mainController.performAction(MainControllerAction.START_PAUSE_MENU);
            }
        });
    }

    private void drawBlocks() {
        this.gameField.getFallenBlocksPoints().forEach(point -> {
            this.drawBlockPoint(point);
        });
        this.gameField.getCurrentBlock().getBlockBody().forEach(point -> {
            this.drawBlockPoint(point);
        });
    }

    private void drawBlockPoint(BlockPoint point) {
        double x = point.getX() * Config.CELL_SIZE;
        double y = point.getY() * Config.CELL_SIZE;
        Color color = point.getColor();
        int width = Config.CELL_SIZE;
        int height = Config.CELL_SIZE;
        int arcWidth = Config.ARC_SIZE;
        int arcHeight = Config.ARC_SIZE;
        this.gc.setFill(color);
        this.gc.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    private void drawBackground() {
        this.gc.setFill(Config.BG_COLOR_2);
        this.gc.fillRect(0, 0, this.windowWidth, this.windowHeight);
        for (int i = 0; i < Config.GAME_FIELD_HEIGHT; i++) {
            for (int j = 0; j < Config.GAME_FIELD_WIDTH; j++) {
                double y = i*Config.CELL_SIZE + 2;
                double x = j*Config.CELL_SIZE + 2;
                int width = Config.CELL_SIZE;
                int height = Config.CELL_SIZE;
                int arcWidth = Config.ARC_SIZE;
                int arcHeight = Config.ARC_SIZE;
                this.gc.setFill(Config.BG_COLOR_1);
                this.gc.fillRoundRect(x, y, width-4, height-4, arcWidth, arcHeight);
            }
        }
    }

    private void drawFutureBlock() {
        Block futureBlock = this.gameField.getCurrentBlock().clone();
        futureBlock.dropDown();
        futureBlock.getBlockBody().forEach(point -> {
            var red = point.getColor().getRed();
            var green = point.getColor().getGreen();
            var blue = point.getColor().getBlue();
            BlockPoint newPoint = new BlockPoint(point.getX(), point.getY(), Color.color(red, green, blue, Config.FUTURE_BLOCK_COLOR_OPACITY));
            this.drawBlockPoint(newPoint);
        });
    }

    private void drawScore() {
        this.gc.setFill(Config.TEXT_COLOR);
        this.gc.setFont(new Font("Segoe UI", 18));
        String text = "Score: " + this.gameField.getGameScore().getScore();
        this.gc.fillText(text, this.windowWidth-100, 40);
    }
}
