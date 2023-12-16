package com.ponggame;

import java.util.LinkedList;
import java.util.Random;

public class Game {

    public Paddle paddle;
    public PaddleMultiplayer paddleMultiplayer;

    private LinkedList<Position> obstacleINV;

    private LinkedList<Position> obstacleCONT;

    public Ball ball;

    private int scorePLAYER1;
    private int scorePLAYER2;


    private final Random rand;

    private boolean MULTIPLAYER;

    public Game(int paddle_size, boolean multiplayer) {
        rand = new Random();
        MULTIPLAYER = multiplayer;

        DirectionBall iDirection = DirectionBall.getRandomDirection();
        ball  = new Ball(iDirection, 41 , 12);

        paddle  = new Paddle(paddle_size);
        paddleMultiplayer = new PaddleMultiplayer(paddle_size);

        obstacleINV = new LinkedList<Position>();
        obstacleCONT = new LinkedList<Position>();

        scorePLAYER1 = 0;
        scorePLAYER2 = 0;
    }

    public LinkedList<Position> getObstacleCONT() {
        return obstacleCONT;
    }

    public LinkedList<Position> getObstacleINV() {
        return obstacleINV;
    }

    public int getScorePLAYER1() {
        return scorePLAYER1;
    }
    public int getScorePLAYER2() {
        return scorePLAYER2;
    }

    public DirectionBall getDirBALL(){
        return ball.getDirection();
    }

    public boolean isBallAlive() {
        return ball.isAlive();
    }

    public void killBall() {
        ball.kill();
    }

    public void moveBall(int selectedLEVEL) {
        ball.move();
        if(selectedLEVEL == 6) {
            if(ball.getBall().getY()==23-1){
                ball.getBall().setY(0);
            }else if(ball.getBall().getY()==-1){
                ball.getBall().setY(23-2);
            }
        }else {
            if (ball.getBall().getY() == 21 || ball.getBall().getY() == 0) {
                if (ball.getDirection() == DirectionBall.LEFT) {
                    ball.setDirectionBall(DirectionBall.RIGHT);
                } else if (ball.getDirection() == DirectionBall.RIGHT) {
                    ball.setDirectionBall(DirectionBall.LEFT);
                } else if (ball.getDirection() == DirectionBall.UL) {
                    ball.setDirectionBall(DirectionBall.DL);
                } else if (ball.getDirection() == DirectionBall.DL) {
                    ball.setDirectionBall(DirectionBall.UL);
                } else if (ball.getDirection() == DirectionBall.UR) {
                    ball.setDirectionBall(DirectionBall.DR);
                } else if (ball.getDirection() == DirectionBall.DR) {
                    ball.setDirectionBall(DirectionBall.UR);
                } else if (ball.getDirection() == DirectionBall.ULL) {
                    ball.setDirectionBall(DirectionBall.DLL);
                } else if (ball.getDirection() == DirectionBall.DLL) {
                    ball.setDirectionBall(DirectionBall.ULL);
                } else if (ball.getDirection() == DirectionBall.URR) {
                    ball.setDirectionBall(DirectionBall.DRR);
                } else if (ball.getDirection() == DirectionBall.DRR) {
                    ball.setDirectionBall(DirectionBall.URR);
                }
            }
        }
    }

    public Position getBall() {
        return ball.getBall();
    }

    public LinkedList<Position> getPaddlePLAYER1Corpo() {
        return paddle.getCorpo();
    }
    public LinkedList<Position> getPaddlePLAYER2Corpo() {
        return paddleMultiplayer.getCorpo();
    }


    public boolean scoredPLAYER1() {
        if (getBall().getX() > paddleMultiplayer.getPontaCima().getX() + 1) {
            updateScorePLAYER1();
            return true;
        }
        return false;
    }
    public boolean scoredPLAYER2() {
        if(getBall().getX() < paddle.getPontaCima().getX()-1) {
            updateScorePLAYER2();
            return true;
        }
        return false;
    }

    public boolean ballColisaoPaddlePLAYER1() {
        return ball.colisionBall(paddle.getCorpo());
    }

    public boolean ballColisaoPaddlePLAYER2() {
        return ball.colisionBall(paddleMultiplayer.getCorpo());
    }


    public boolean ballColisaoObstacleINV() {
        return ball.colisionObstINV(obstacleINV);
    }

    public boolean ballColisaoObstacleCONT() {
        return ball.colisionObstCONT(obstacleCONT);
    }

    public void updateScorePLAYER1() {
        scorePLAYER1 += 1;
    }
    public void updateScorePLAYER2() {
        scorePLAYER2 += 1;
    }

    private int randomNumber(int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    private Position generateRandomPosition(int max_x, int min_x, int max_y, int min_y) {
        int x = randomNumber(min_x, max_x);
        int y = randomNumber(min_y, max_y);

        return new Position(x, y);
    }

    public boolean isEmptyPosition(Position p) {
        return !(obstacleCONT.contains(p) || obstacleINV.contains(p));
    }

    public Position generateRandomObject(int x_max, int x_min, int y_max, int y_min) {
        Position p = null;

        do {
            p = generateRandomPosition(x_max, x_min, y_max, y_min);

        } while(!isEmptyPosition(p));

        return p;
    }
}
