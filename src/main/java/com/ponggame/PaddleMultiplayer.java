package com.ponggame;

import java.util.LinkedList;

public class PaddleMultiplayer {

    private DirectionPaddle direction;
    private static LinkedList<Position> corpo;

    private int paddleSize;

    public PaddleMultiplayer(int paddle_size) {
        corpo = new LinkedList<>();

        for(int i = 0; i < paddle_size; i++)
            corpo.add(new Position(77, 7 + i + 1));

        paddleSize = paddle_size;

    }

    public LinkedList<Position> getCorpo()
    {
        return corpo;
    }

    public Position getPontaCima()
    {
        return corpo.getLast();
    }

    public Position getPontaBaixo()
    {
        return corpo.getFirst();
    }

    public void setDirection(DirectionPaddle direction)
    {
        this.direction = direction;
    }

    public DirectionPaddle getDirection()
    {
        return direction;
    }

    public int getPaddleSize() { return this.paddleSize;}

}