package com.ponggame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class TestGame {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game(5, true);
    }

    @Test
    public void initialScorePLAYER1() {
        Assertions.assertEquals(0, game.getScorePLAYER1());
    }

    @Test
    public void initialScorePLAYER2() {
        Assertions.assertEquals(0, game.getScorePLAYER2());
    }

    @Test
    public void isBallAlive() {
        Assertions.assertTrue(game.isBallAlive());
    }

    @Test
    public void ballKill() {
        game.killBall();

        Assertions.assertFalse(game.isBallAlive());
    }

    @Test
    public void paddlePLAYER1Size() {
        Assertions.assertEquals(5, game.getPaddlePLAYER1Corpo().size());
    }

    @Test
    public void paddlePLAYER2Size() {
        Assertions.assertEquals(5, game.getPaddlePLAYER2Corpo().size());
    }

    @Test
    public void ballPosition() {
        Position p = new Position(41,12);

        Assertions.assertEquals(p, game.getBall());
    }

    @Test
    public void setPaddlePLAYER1direction() {
        game.paddle.setDirection(DirectionPaddle.UP);

        Assertions.assertEquals(DirectionPaddle.UP, game.paddle.getDirection());
    }

    @Test
    public void player1Scored(){
        game.getBall().setX(81);
        game.getBall().setY(12);

        game.scoredPLAYER1();

        Assertions.assertEquals(1, game.getScorePLAYER1());
    }

    @Test
    public void player2Scored(){
        game.getBall().setX(0);
        game.getBall().setY(12);

        game.scoredPLAYER2();

        Assertions.assertEquals(1, game.getScorePLAYER2());
    }

    @Test
    public void ballMove(){
        game.ball.setDirectionBall(DirectionBall.UL);
        game.getBall().setX(36);
        game.getBall().setY(1);

        game.moveBall(5);

        Assertions.assertEquals(DirectionBall.DL, game.ball.getDirection());
    }
}
