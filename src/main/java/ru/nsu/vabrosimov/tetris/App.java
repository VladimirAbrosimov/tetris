package ru.nsu.vabrosimov.tetris;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.nsu.vabrosimov.tetris.controller.MainController;


public class App extends Application {
    @Override
    public void start(Stage stage) {
        MainController mainController = new MainController();
        mainController.start();
    }

    public static void main(String[] args) {
        launch();
    }

}