package com.ponggame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static com.ponggame.DirectionBall.*;

public class TestBall {

    private Ball ball;
    @BeforeEach
    public void setUp() {
        DirectionBall iDirection = DirectionBall.getRandomDirection();
        ball = new Ball(iDirection, 41, 12);
    }

    @Test
    public void positionBall() {
        Position p = new Position(41,12);
        Assertions.assertEquals(p,ball.getBall());
    }

    @Test
    public void changeDirection() {
        ball.setDirectionBall(DR);
        Assertions.assertEquals(DR, ball.getDirection());
    }
    @Test
    public void ballIsAlive() {
        Assertions.assertTrue(ball.isAlive());
    }

    @Test
    public void killBall() {
        ball.kill();
        Assertions.assertFalse(ball.isAlive());
    }

    @Test
    public void moveBall() {
        Position p = new Position(41, 12);

        ball.getBall().setX(40);
        ball.getBall().setY(13);

        ball.setDirectionBall(UR);
        ball.move();

        Assertions.assertEquals(p,ball.getBall());
    }

    @Test
    public void colisionPaddle() {
        ball.setDirectionBall(RIGHT);
        Position p = new Position(41, 12);

        LinkedList<Position> paddle = new LinkedList<>();
        paddle.addLast(p);

        Assertions.assertTrue(ball.colisionBall(paddle));
    }

    @Test
    public void colisionobstacles() {
        Position p = new Position(ball.getBall().getX(), ball.getBall().getY());
        LinkedList<Position> obstacle = new LinkedList<>();
        obstacle.addLast(p);

        Assertions.assertTrue(ball.colisionObstCONT(obstacle));
    }

    @Test
    public void ballrandomDirectionRight() {
        boolean dir = false;
        DirectionBall directionBall = DirectionBall.getRandomDirectionRIGHT();
        ball.setDirectionBall(directionBall);
        if(ball.getDirection()==DirectionBall.RIGHT || ball.getDirection()==UR || ball.getDirection()==DR || ball.getDirection()==DirectionBall.URR || ball.getDirection()==DirectionBall.DRR) {
            dir=true;
        }
        Assertions.assertTrue(dir);
    }

    @Test
    public void ballrandomDirectionLeft() {
        boolean dir = false;
        DirectionBall directionBall = DirectionBall.getRandomDirectionLEFT();
        ball.setDirectionBall(directionBall);
        if(ball.getDirection()==DirectionBall.LEFT || ball.getDirection()==UL || ball.getDirection()==DL || ball.getDirection()==DirectionBall.ULL || ball.getDirection()==DirectionBall.DLL) {
            dir=true;
        }
        Assertions.assertTrue(dir);
    }
}
