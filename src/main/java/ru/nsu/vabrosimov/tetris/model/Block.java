package ru.nsu.vabrosimov.tetris.model;


import javafx.scene.paint.Color;
import ru.nsu.vabrosimov.tetris.Config;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Block {
    private Color color;
    private BlockType blockType;
    private ArrayList<BlockPoint> blockBody;
    private ArrayList<BlockPoint> fallenBlocksPoints;

    private static int nextColor = new Random().nextInt(Config.BLOCKS_COLORS.length);

    public Block() {

    }

    public Block(ArrayList<BlockPoint> fallenBlocksPoints) {
        this.initColor();
        this.initBlockType();
        this.initBlockBody(this.blockType);
        this.moveToCenter();
        this.fallenBlocksPoints = fallenBlocksPoints;
    }

    @Override
    public Block clone() {
        Block blockClone = new Block();
        blockClone.color = this.color;
        blockClone.blockType = this.blockType;
        blockClone.blockBody = new ArrayList<>();
        for (int i = 0; i < this.blockBody.size(); i++) {
            blockClone.blockBody.add(new BlockPoint(this.blockBody.get(i)));
        }
        blockClone.fallenBlocksPoints = this.fallenBlocksPoints;
        return blockClone;
    }

    public ArrayList<BlockPoint> getBlockBody() {
        return this.blockBody;
    }

    public void moveLeft() {
        if (this.isPressedToLeftBorder() || this.isPressedToFallenBlocksOnLeft()) {
            return;
        }
        this.blockBody.forEach(e -> e.moveX(-1));
    }

    public void moveRight() {
        if (this.isPressedToRightBorder() || this.isPressedToFallenBlocksOnRight()) {
            return;
        }
        this.blockBody.forEach(e -> e.moveX(1));
    }

    public void moveDown() {
        if (this.isPressedToBottomBorder() || this.isPressedToFallenBlocksOnBottom()) {
            return;
        }
        this.blockBody.forEach(e -> e.moveY(1));
    }

    public void rotate() {
        var blockClone = this.clone();
        int oldLeftX = this.countLeftX();
        int oldTopY = this.countTopY();

        blockClone.blockBody.forEach(point -> point.rotate(Math.PI/2));

        int newLeftX = blockClone.countLeftX();
        int newTopY = blockClone.countTopY();

        blockClone.blockBody.forEach(point -> {
            point.setPosition(
                    point.getX() + (oldLeftX - newLeftX),
                    point.getY() + (oldTopY - newTopY));
        });
        if (!blockClone.isOverlappedOnFallenBlocks() && !blockClone.isOutOfBorder()) {
            for (int i = 0; i < this.blockBody.size(); i++) {
                this.blockBody.set(i, blockClone.getBlockBody().get(i));
            }
        }
    }

    public void dropDown() {
        while (!this.isFall()) {
            this.moveDown();
        }
    }

    public boolean isFall() {
        return isPressedToBottomBorder() || isPressedToFallenBlocksOnBottom();
    }

    private int countLeftX() {
        var leftX = this.blockBody
                .stream().min(Comparator.comparing(BlockPoint::getX)).get().getX();
        return leftX;
    }

    private int countTopY() {
        var topY = this.blockBody
                .stream().min(Comparator.comparing(BlockPoint::getY)).get().getY();
        return topY;
    }

    private boolean isOverlappedOnFallenBlocks() {
        for (var i : this.blockBody) {
            for (var j : this.fallenBlocksPoints) {
                if (i.equals(j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isOutOfBorder() {
        return this.blockBody.stream().anyMatch(
                point -> point.getX() < 0 || point.getX() >= Config.GAME_FIELD_WIDTH ||
                         point.getY() < 0 || point.getY() >= Config.GAME_FIELD_HEIGHT
        );
    }


    private boolean isPressedToLeftBorder() {
        return this.blockBody.stream().anyMatch(
                point -> point.getX() == 0
        );
    }

    private boolean isPressedToRightBorder() {
        return this.blockBody.stream().anyMatch(
                point -> point.getX() == Config.GAME_FIELD_WIDTH - 1
        );
    }

    private boolean isPressedToBottomBorder() {
        return this.blockBody.stream().anyMatch(
                point -> point.getY() == Config.GAME_FIELD_HEIGHT - 1
        );
    }

    private boolean isPressedToFallenBlocksOnLeft() {
        for (BlockPoint i : this.blockBody)  {
            for (BlockPoint j : this.fallenBlocksPoints)  {
                if (i.getX() == j.getX() + 1 && i.getY() == j.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPressedToFallenBlocksOnRight() {
        for (BlockPoint i : this.blockBody)  {
            for (BlockPoint j : this.fallenBlocksPoints)  {
                if (i.getX() == j.getX() - 1 && i.getY() == j.getY()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isPressedToFallenBlocksOnBottom() {
        for (BlockPoint i : this.blockBody)  {
            for (BlockPoint j : this.fallenBlocksPoints)  {
                if (i.getX() == j.getX() && i.getY() == j.getY() - 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private void initColor() {
        this.color = Config.BLOCKS_COLORS[this.nextColor];
        this.nextColor = (this.nextColor + 1) % Config.BLOCKS_COLORS.length;
    }

    private void initBlockType() {
        int pick = new Random().nextInt(BlockType.values().length);
        this.blockType = BlockType.values()[pick];
    }

    private void moveToCenter() {
        this.blockBody.forEach(point -> {
            point.moveX(((int)Config.GAME_FIELD_WIDTH/2)-1);
        });
    }

    private void initBlockBody(BlockType blockType) {
        this.blockBody = new ArrayList<>();
        if (blockType == BlockType.I) {
            this.blockBody.add(new BlockPoint(0,0, this.color));
            this.blockBody.add(new BlockPoint(1,0, this.color));
            this.blockBody.add(new BlockPoint(2,0, this.color));
            this.blockBody.add(new BlockPoint(3,0, this.color));
        }
        else if (blockType == BlockType.J) {
            this.blockBody.add(new BlockPoint(0,0, this.color));
            this.blockBody.add(new BlockPoint(1,0, this.color));
            this.blockBody.add(new BlockPoint(2,0, this.color));
            this.blockBody.add(new BlockPoint(2,1, this.color));
        }
        else if (blockType == BlockType.L) {
            this.blockBody.add(new BlockPoint(0,0, this.color));
            this.blockBody.add(new BlockPoint(1,0, this.color));
            this.blockBody.add(new BlockPoint(2,0, this.color));
            this.blockBody.add(new BlockPoint(0,1, this.color));
        }
        else if (blockType == BlockType.O) {
            this.blockBody.add(new BlockPoint(0,0, this.color));
            this.blockBody.add(new BlockPoint(1,0, this.color));
            this.blockBody.add(new BlockPoint(0,1, this.color));
            this.blockBody.add(new BlockPoint(1,1, this.color));
        }
        else if (blockType == BlockType.S) {
            this.blockBody.add(new BlockPoint(1,0, this.color));
            this.blockBody.add(new BlockPoint(2,0, this.color));
            this.blockBody.add(new BlockPoint(0,1, this.color));
            this.blockBody.add(new BlockPoint(1,1, this.color));
        }
        else if (blockType == BlockType.T) {
            this.blockBody.add(new BlockPoint(1,0, this.color));
            this.blockBody.add(new BlockPoint(0,1, this.color));
            this.blockBody.add(new BlockPoint(1,1, this.color));
            this.blockBody.add(new BlockPoint(2,1, this.color));
        }
        else if (blockType == BlockType.Z) {
            this.blockBody.add(new BlockPoint(0,0, this.color));
            this.blockBody.add(new BlockPoint(1,0, this.color));
            this.blockBody.add(new BlockPoint(1,1, this.color));
            this.blockBody.add(new BlockPoint(2,1, this.color));
        }
    }

}
