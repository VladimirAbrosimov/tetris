package ru.nsu.vabrosimov.tetris.controller;

import ru.nsu.vabrosimov.tetris.view.PauseMenuView;

public class PauseMenuController {
    private MainController mainController;
    private PauseMenuView pauseMenuView;

    public PauseMenuController(MainController mainController) {
        this.mainController = mainController;
        this.pauseMenuView = new PauseMenuView(this.mainController);
    }

    public void startWindow() {
        this.pauseMenuView.initWindow();
    }
}
