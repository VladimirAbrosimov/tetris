package ru.nsu.vabrosimov.tetris.model;

import ru.nsu.vabrosimov.tetris.Config;
import ru.nsu.vabrosimov.tetris.view.GameView;

import java.util.*;

public class GameField {
    private Block currentBlock;
    private ArrayList<BlockPoint> fallenBlocksPoints;
    private GameScore gameScore;
    private Map<GameAction, Runnable> gameActionRunnableMap;
    private GameView gameView;

    public GameField() {
        this.fallenBlocksPoints = new ArrayList<>();
        this.currentBlock = new Block(this.fallenBlocksPoints);
        this.gameScore = new GameScore();
        this.initGameActionRunnableMap();
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    private void initGameActionRunnableMap() {
        this.gameActionRunnableMap = new HashMap<>();
        this.gameActionRunnableMap.put(
                GameAction.MOVE_LEFT, this::moveLeftCurrentBlock);
        this.gameActionRunnableMap.put(
                GameAction.MOVE_RIGHT, this::moveRightCurrentBlock);
        this.gameActionRunnableMap.put(
                GameAction.DROP_DOWN, this::dropDownCurrentBlock);
        this.gameActionRunnableMap.put(
                GameAction.ROTATE, this::rotateCurrentBlock);
    }

    public Block getCurrentBlock() {
        return this.currentBlock;
    }

    public GameScore getGameScore() {
        return this.gameScore;
    }

    public ArrayList<BlockPoint> getFallenBlocksPoints() {
        return this.fallenBlocksPoints;
    }

    public void nextStep() {
        if (this.currentBlock.isFall()) {
            this.addScoreForBlockFall();
            this.fallenBlocksPoints.addAll(currentBlock.getBlockBody());
            this.deleteFilledLines();
            currentBlock = new Block(this.fallenBlocksPoints);
        } else {
            this.moveDownCurrentBlock();
        }
    }

    public void performAction(GameAction gameAction) {
        if (this.gameActionRunnableMap.containsKey(gameAction)) {
            this.gameActionRunnableMap.get(gameAction).run();
        }
        this.gameView.updateWindow();
    }

    public boolean isLose() {
        boolean isLose = this.fallenBlocksPoints
                .stream().anyMatch(point -> point.getY() <= 0);
        return isLose;
    }

    private void deleteFilledLines() {
        var filledLines = this.getFilledLines();

        this.addScoreForNFilledLines(filledLines.size());

        filledLines.forEach(line -> {
            this.deleteLine(line);
            this.moveDownPointsAboveLine(line);
        });

    }

    private ArrayList<Integer> getFilledLines() {
        ArrayList<Integer> filledLines = new ArrayList<>();
        for (BlockPoint i : this.currentBlock.getBlockBody()) {
            int y = i.getY();
            if (filledLines.stream().anyMatch(line -> line == y)) {
                continue;
            }

            int blocksInLineCount = 0;
            for (int j = 0; j < Config.GAME_FIELD_WIDTH; j++) {
                int x = j;
                if (this.fallenBlocksPoints
                        .stream().anyMatch(point -> point.equals(new BlockPoint(x, y, point.getColor())))) {
                    blocksInLineCount++;
                }
            }
            if (blocksInLineCount == Config.GAME_FIELD_WIDTH) {
                filledLines.add(y);
            }
        }
        return filledLines;
    }

    private void deleteLine(int line) {
        Iterator<BlockPoint> i = this.fallenBlocksPoints.iterator();
        while (i.hasNext()) {
            var point = i.next();
            if (point.getY() == line) {
                i.remove();
            }
        }
    }

    private void moveDownPointsAboveLine(int line) {
        this.fallenBlocksPoints.forEach(point -> {
            if (point.getY() < line) {
                point.moveY(1);
            }
        });
    }


    private void moveLeftCurrentBlock() {
        this.currentBlock.moveLeft();
    }

    private void moveRightCurrentBlock() {
        this.currentBlock.moveRight();
    }

    private void moveDownCurrentBlock() {
        this.currentBlock.moveDown();
    }

    private void rotateCurrentBlock() {
        this.currentBlock.rotate();
    }

    private void dropDownCurrentBlock() {
        this.addScoreForBlockDrop();
        this.currentBlock.dropDown();
    }

    private void addScoreForBlockFall() {
        this.gameScore.increase(Config.SCORE_FOR_BLOCK_FALL);
    }

    private void addScoreForNFilledLines(int countFilledLines) {
        this.gameScore.increase(
                countFilledLines * Config.SCORE_FOR_FILLED_LINES_BASE
                    * (1 + countFilledLines/Config.GAME_FIELD_HEIGHT*5));
    }

    private void addScoreForBlockDrop() {
        this.gameScore.increase(
                Config.SCORE_FOR_BLOCK_DROP_BASE * this.countDistanceToObstacleBelow()
                        / Config.GAME_FIELD_HEIGHT * 2);
    }

    private int countDistanceToObstacleBelow() {
        var currentBlockClone = this.currentBlock.clone();
        currentBlockClone.dropDown();
        int distance = currentBlockClone.getBlockBody().get(0).getY() -
                currentBlock.getBlockBody().get(0).getY();
        return distance;
    }
}
