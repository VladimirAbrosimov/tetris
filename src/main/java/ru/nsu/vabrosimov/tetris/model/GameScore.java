package ru.nsu.vabrosimov.tetris.model;

public class GameScore {
    private int score;

    public GameScore() {
        this.score = 0;
    }

    public void increase(int delta) {
        this.score += delta;
    }

    public int getScore() {
        return this.score;
    }
}
