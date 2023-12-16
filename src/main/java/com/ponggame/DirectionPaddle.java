package com.ponggame;

import java.util.Random;

public enum DirectionPaddle {
    UP, DOWN;

    private static final DirectionPaddle[] VALUES = values();
    private static final int SIZE = VALUES.length;
    private static final Random RANDOM = new Random();
    public static DirectionPaddle getRandomDirection()  {
        return VALUES[RANDOM.nextInt(SIZE)];
    }
}
