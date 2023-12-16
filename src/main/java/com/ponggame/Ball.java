package com.ponggame;

import java.util.LinkedList;

public class Ball {
    private DirectionBall directionBall;
    private static LinkedList<Position> ball;
    private boolean alive;

    public Ball(DirectionBall starting_direction, int startingX_position, int startingY_position) {
        ball = new LinkedList<>();
        ball.add(new Position(startingX_position, startingY_position));

        directionBall = starting_direction;
        alive = true;
    }

    public Position getBall()
    {
        return ball.getLast();
    }

    public void setDirectionBall(DirectionBall direction)
    {
        this.directionBall = direction;
    }

    public DirectionBall getDirection()
    {
        return directionBall;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public void kill()
    {
        alive = false;
    }

    public void move() {

        Position ball_position = getBall();

        ball.removeFirst();

        ball_position = switch (directionBall) {
            case LEFT -> new Position(ball_position.getX() - 1, ball_position.getY());
            case RIGHT -> new Position(ball_position.getX() + 1, ball_position.getY());
            case UL -> new Position(ball_position.getX() - 1, ball_position.getY() - 1);
            case UR -> new Position(ball_position.getX() + 1, ball_position.getY() - 1);
            case DL -> new Position(ball_position.getX() - 1, ball_position.getY() + 1);
            case DR -> new Position(ball_position.getX() + 1, ball_position.getY() + 1);
            case ULL -> new Position(ball_position.getX() - 2, ball_position.getY() - 1);
            case DLL -> new Position(ball_position.getX() - 2, ball_position.getY() + 1);
            case URR -> new Position(ball_position.getX() + 2, ball_position.getY() - 1);
            case DRR -> new Position(ball_position.getX() + 2, ball_position.getY() + 1);
        };

        ball.addLast(ball_position);
    }

    public boolean colisionBall(LinkedList<Position> obstaculos) {
        Position ball_position = getBall();

       if(getDirection() == DirectionBall.DLL || getDirection() == DirectionBall.ULL){
           for(Position p : obstaculos) {
               if(ball_position.getX() - 1 == p.getX() && (ball_position.getY() == p.getY())) {
                   return true;
               }else if(ball_position.getX() + 1 == p.getX() + 1 && (ball_position.getY() == p.getY())) {
                   return true;
               }

           }
       }else if(getDirection() == DirectionBall.DRR || getDirection() == DirectionBall.URR){
            for(Position p : obstaculos) {
                if(ball_position.getX() + 1 == p.getX() && (ball_position.getY() == p.getY())) {
                    return true;

                }else if(ball_position.getX() + 1 == p.getX() - 1 && (ball_position.getY() == p.getY())) {
                    return true;

                }

                if(ball_position.getX() + 1 == p.getX() - 1 && (ball_position.getY() == p.getY())) {
                    return true;
                }
            }
       }else {
           for (Position p : obstaculos) {
               if (ball_position.equals(p)) {
                   return true;
               }
               if(ball_position.getX() + 1 == p.getX() + 1 && (ball_position.getY() == p.getY())) {
                   return true;
               }

           }
       }
        return false;
    }

    public boolean colisionObstINV(LinkedList<Position> obstacle) {
        Position ball_position = getBall();

        for(Position p : obstacle) {
            if(ball_position.equals(p)) {
                obstacle.remove(p);
                return true;
            }
        }

        return false;
    }

    public boolean colisionObstCONT(LinkedList<Position> obstacle) {
        Position ball_position = getBall();

        for(Position p : obstacle) {
            if(ball_position.equals(p)) {
                obstacle.remove(p);
                return true;
            }
        }

        return false;
    }
}
