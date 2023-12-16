package com.ponggame;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class TestGameSinglePlayer {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = new Game(5, false);
    }

    @Test
    public void initialScoreComputer() {
        Assertions.assertEquals(0, game.getScorePLAYER2());
    }

    @Test
    public void paddleComputerSize() {
        Assertions.assertEquals(5, game.getPaddlePLAYER2Corpo().size());
    }

    @Test
    public void computerScored(){
        game.getBall().setX(0);
        game.getBall().setY(12);

        game.scoredPLAYER2();

        Assertions.assertEquals(1, game.getScorePLAYER2());
    }

}
