package ru.nsu.vabrosimov.tetris.model;

public class IntegerPoint {
    private int x;
    private int y;

    public IntegerPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntegerPoint(IntegerPoint point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        IntegerPoint point = (IntegerPoint) obj;
        return (point.getX() == this.getX() && point.getY() == this.getY());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveX(int offset) {
        this.x += offset;
    }

    public void moveY(int offset) {
        this.y += offset;
    }

    public void rotate(double angle) {
        this.setPosition(
                this.getX()*(int)Math.cos(angle) - this.getY()*(int)Math.sin(angle),
                this.getY()*(int)Math.cos(angle) + this.getX()*(int)Math.sin(angle)
        );
    }
}
