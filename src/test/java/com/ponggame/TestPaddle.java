package com.ponggame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static com.ponggame.DirectionBall.DL;
import static com.ponggame.DirectionBall.UL;

public class TestPaddle {
    private Paddle paddle;
    @BeforeEach
    public void setUp() {
        paddle = new Paddle(5);
        paddle.setDirection(DirectionPaddle.UP);
    }

    @Test
    public void getPaddle() {
        Assertions.assertEquals(5, paddle.getPaddleSize());
    }

    @Test
    public void getPaddleDirection() {
        Assertions.assertEquals(DirectionPaddle.UP, paddle.getDirection());
    }

    @Test
    public void getPontaBaixoPaddle() {
        Assertions.assertEquals(new Position(4,8), paddle.getPontaBaixo());
    }

    @Test
    public void paddlerandomDirection() {
        boolean dir = false;
        DirectionPaddle directionPaddle = DirectionPaddle.getRandomDirection();
        paddle.setDirection(directionPaddle);
        if(paddle.getDirection()==DirectionPaddle.DOWN || paddle.getDirection()==DirectionPaddle.UP) {
            dir=true;
        }
        Assertions.assertTrue(dir);
    }
}