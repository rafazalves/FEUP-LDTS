package com.ponggame;

public class Application {
    private final static int LARGE_WIDTH  = 81;
    private final static int LARGE_HEIGHT = 23;

    public static void main(String[] args) {
        GameBuild pong_game = new GameBuild(LARGE_WIDTH, LARGE_HEIGHT);
        pong_game.openMainMenu();
    }
}