package com.ponggame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class TestPaddleMultiplayer {
    private PaddleMultiplayer paddleMultiplayer;
    @BeforeEach
    public void setUp() {
        paddleMultiplayer = new PaddleMultiplayer(5);
        paddleMultiplayer.setDirection(DirectionPaddle.UP);
    }
    @Test
    public void getPaddle() {
        Assertions.assertEquals(5, paddleMultiplayer.getPaddleSize());
    }

    @Test
    public void getPaddleDirection() {
        Assertions.assertEquals(DirectionPaddle.UP, paddleMultiplayer.getDirection());
    }

    @Test
    public void getPontaBaixoPaddle() {
        Assertions.assertEquals(new Position(77,8), paddleMultiplayer.getPontaBaixo());
    }
}
