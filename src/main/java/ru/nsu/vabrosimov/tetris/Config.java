package ru.nsu.vabrosimov.tetris;

import javafx.scene.paint.Color;

public class Config {
    public static final int TIME_UPDATE = 180;
    public static final int CELL_SIZE = 40;
    public static final int GAME_FIELD_WIDTH = 12; // in cells
    public static final int GAME_FIELD_HEIGHT = 22; // ins cells
    public static final int ARC_SIZE = 9;
    public static final Color[] BLOCKS_COLORS = {
            Color.web("0x0c48cd"),
            Color.web("0xf11433"),
            Color.web("0xfcb913"),
            Color.web("0x74e91e"),
            Color.web("0x10b8ff"),
            Color.web("0xbd1e95"),
            Color.web("0xff7910"),
    };
    public static final Color BG_COLOR_1 = Color.web("0x0d0d0d");
    public static final Color BG_COLOR_2 = Color.web("0x000000");
    public static final double FUTURE_BLOCK_COLOR_OPACITY = 0.25;
    public static final Color TEXT_COLOR = Color.web("0xddd");

    public static final int SCORE_FOR_FILLED_LINES_BASE = 100;
    public static final int SCORE_FOR_BLOCK_FALL = 10;
    public static final int SCORE_FOR_BLOCK_DROP_BASE = 10;

}
