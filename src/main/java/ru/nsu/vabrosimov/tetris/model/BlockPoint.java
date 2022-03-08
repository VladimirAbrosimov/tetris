package ru.nsu.vabrosimov.tetris.model;

import javafx.scene.paint.Color;

public class BlockPoint extends IntegerPoint {
    private Color color;

    public BlockPoint(int x, int y, Color color) {
        super(x,y);
        this.color = color;
    }

    public BlockPoint(BlockPoint point) {
        super(point);
        this.color = point.color;
    }

    public Color getColor() {
        return this.color;
    }
}
