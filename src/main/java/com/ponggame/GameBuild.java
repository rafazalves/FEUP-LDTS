package com.ponggame;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.Terminal.Color;
import com.googlecode.lanterna.terminal.swing.SwingTerminal;

import java.util.LinkedList;

import static com.ponggame.DirectionBall.*;

public class GameBuild {
    private final static String EMPTY_STRING = " ";

    private final static String GAMEMID_STRING = "|";
    private final static String LIMIT_STRING = "_";

    private final static String PADDLE_STRING = "#";
    private final static String BALL_STRING = "o";
    private final static String OBSTACLEINV_STRING = "X";
    private final static String OBSTACLECONT_STRING = "$";

    // Valor minimo para as coordenadas x e y
    private final static int X_COORDINATE_OFFSET = 1;
    private final static int Y_COORDINATE_OFFSET = 2;

    // Velocidade da Bola no jogo
    private final static int GAME_LEVEL_1 = 95;
    private final static int GAME_LEVEL_2 = 80;
    private final static int GAME_LEVEL_3 = 65;
    private final static int GAME_LEVEL_4 = 50;
    private final static int GAME_LEVEL_5 = 35;

    // Posicao de cada opcao no menu principal
    private final static int OPTION_LEVEL_1 = 32;
    private final static int OPTION_LEVEL_2 = 34;
    private final static int OPTION_LEVEL_3 = 36;
    private final static int OPTION_LEVEL_4 = 38;
    private final static int OPTION_LEVEL_5 = 40;
    private final static int OPTION_LEVEL_6 = 42;
    private final static int INSTRUCTIONS_GAME = 14;
    private final static int QUIT_GAME      = 17;

    // Posicao de cada opcao no menu de GAME OVER (START_GAME também no menu principal)
    private final static int START_GAME   = 1;

    private final static int CREATE_LEVEL = 2;
    private final static int RESTART_GAME = 15;
    private final static int MAIN_MENU    = 16;

    private final static int SINGLEPLAYER = 18;


    // Valores das escolhas do Menu CREATE LEVEL
    private final int CHOOSE_NUMBER_OF_OBSTACLES = 9;
    private final int CHOOSE_DIFFICULTY = 10;
    private final int CHOOSE_WALLS = 11;
    private final int CHOOSE_MULT = 12;
    private final int CHOOSE_PADDLE_SIZE = 13;
    private final int CHOOSE_TARGET_SCORE = 14;
    private final int CHOOSE_START_GAME = 15;

    public long contador = 0;
    private long time0;
    private long time1;

    private int DIFFICULTY = 1;
    private int TARGET_SCORE = 10;
    private int PADDLE_SIZE = 5;
    private int NUMBER_OF_OBSTACLES = 3;
    private int WALLS = 1;

    private boolean createlevel = false;
    private boolean mult = true;

    // Terminal da Lanterna
    private static SwingTerminal terminal;

    // Screen usado no terminal da Lanterna
    private static Screen screen;

    private Game state;

    // Dimensoes do jogo
    public int gameplay_height;
    public int gameplay_width;

    // Nivel selecionado pelo utilizador
    private int nivelSelecionado;


    public GameBuild(int width, int height) {
        terminal = new SwingTerminal(width, height);

        gameplay_width  = width  - X_COORDINATE_OFFSET;
        gameplay_height = height - Y_COORDINATE_OFFSET;

        screen = new Screen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
    }

    private void startGame() {
        // Determina a frequencia com que o jogo necessita de ser atualizado
        contador = 0;

        time0 = System.nanoTime();

        drawMidField();
        drawPaddle();
        drawBall();

        drawTargetScore();
        drawString(6, gameplay_height + 1, "SCORE PLAYER 1: ", Color.CYAN);
        drawScorePLAYER1(); // score inicial (score será 0)

        if(mult){
            drawPaddleMultiplayer();
            drawString(58, gameplay_height + 1, "SCORE PLAYER 2: ", Color.CYAN);
            drawScorePLAYER2(); // score inicial (score será 0)
        }else{
            drawPaddleMultiplayer();
            drawString(58, gameplay_height + 1, "SCORE COMPUTER: ", Color.CYAN);
            drawScoreComputer(); // score inicial (score será 0)
        }


        while(state.isBallAlive()) {

            if(createlevel) {
                if (contador == 0) {
                    if (NUMBER_OF_OBSTACLES % 2 == 0) {
                        for (int i = 0; i < NUMBER_OF_OBSTACLES / 2; i++) {
                            generateNewObstacleINV();
                            generateNewObstacleCONT();
                        }
                    } else {
                        for (int i = 0; i < NUMBER_OF_OBSTACLES / 2; i++) {
                            generateNewObstacleINV();
                            generateNewObstacleCONT();
                        }
                        generateNewObstacleCONT();
                    }
                }
            }

            readKeyboard();

            if(contador % nivelSelecionado == 0)
                updateGame();

            try {
                Thread.sleep(1);
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            contador++;
        }
    }

    private void updateGame() {
        Position ball = state.getBall();
        clearStringAt(ball.getX(), ball.getY());

        drawMidField();
        drawPaddle();
        drawScorePLAYER1();

        if(mult) {
            drawPaddleMultiplayer();
            drawScorePLAYER2();
        }
        else {
            movePaddleComputer(state.getPaddlePLAYER2Corpo(), DirectionPaddle.getRandomDirection());
            drawPaddleMultiplayer();
            drawScoreComputer();
        }
        drawTargetScore();

        if(WALLS==0) {
            state.moveBall(6);
        }else{
            state.moveBall(nivelSelecionado);
        }
        drawBall();

        if(state.ballColisaoObstacleCONT()){
            DirectionBall iDirection;
            if(state.ball.getDirection()==LEFT || state.ball.getDirection()==UL || state.ball.getDirection()==DL || state.ball.getDirection()==ULL || state.ball.getDirection()==DLL){
                iDirection = DirectionBall.getRandomDirectionLEFT();
            }else{
                iDirection = DirectionBall.getRandomDirectionRIGHT();
            }
            state.ball.setDirectionBall(iDirection);
        }else if(state.ballColisaoObstacleINV()){
            DirectionBall iDirection;
            if(state.ball.getDirection()==LEFT || state.ball.getDirection()==UL || state.ball.getDirection()==DL || state.ball.getDirection()==ULL || state.ball.getDirection()==DLL){
                iDirection = DirectionBall.getRandomDirectionRIGHT();
            }else{
                iDirection = DirectionBall.getRandomDirectionLEFT();
            }
            state.ball.setDirectionBall(iDirection);
        }

        if(mult){
            if(state.ballColisaoPaddlePLAYER1()){
                DirectionBall iDirection = DirectionBall.getRandomDirectionRIGHT();
                state.ball.setDirectionBall(iDirection);
            }else if(state.ballColisaoPaddlePLAYER2()){
                DirectionBall iDirection = DirectionBall.getRandomDirectionLEFT();
                state.ball.setDirectionBall(iDirection);
            }

            if(state.scoredPLAYER1()){
                drawScorePLAYER1();
                DirectionBall iDirection = DirectionBall.getRandomDirectionLEFT();
                state.ball.setDirectionBall(iDirection);
                clearStringAt(state.getBall().getX(),state.getBall().getY());
                state.getBall().setX(gameplay_width/2);
                state.getBall().setY(gameplay_height/2);
            }else if(state.scoredPLAYER2()){
                drawScorePLAYER2();
                DirectionBall iDirection = DirectionBall.getRandomDirectionRIGHT();
                state.ball.setDirectionBall(iDirection);
                clearStringAt(state.getBall().getX(),state.getBall().getY());
                state.getBall().setX(gameplay_width/2);
                state.getBall().setY(gameplay_height/2);
            }

            if (state.getScorePLAYER1() >= TARGET_SCORE) {
                state.killBall();

                time1 = System.nanoTime();

                contador = (time1-time0)/1000000;

                clearScreen();

                openGameWinPLAYER1Menu();
            }else if (state.getScorePLAYER2() >= TARGET_SCORE) {
                state.killBall();

                time1 = System.nanoTime();

                contador = (time1-time0)/1000000;

                clearScreen();

                openGameWinPLAYER2Menu();
            }

        }else{
            if(state.ballColisaoPaddlePLAYER1()){
                DirectionBall iDirection = DirectionBall.getRandomDirectionRIGHT();
                state.ball.setDirectionBall(iDirection);
            }else if(state.ballColisaoPaddlePLAYER2()){
                DirectionBall iDirection = DirectionBall.getRandomDirectionLEFT();
                state.ball.setDirectionBall(iDirection);
            }

            if(state.scoredPLAYER1()){
                drawScorePLAYER1();
                DirectionBall iDirection = DirectionBall.getRandomDirectionLEFT();
                state.ball.setDirectionBall(iDirection);
                clearStringAt(state.getBall().getX(),state.getBall().getY());
                state.getBall().setX(gameplay_width/2);
                state.getBall().setY(gameplay_height/2);
            }else if(state.scoredPLAYER2()){
                drawScoreComputer();
                DirectionBall iDirection = DirectionBall.getRandomDirectionRIGHT();
                state.ball.setDirectionBall(iDirection);
                clearStringAt(state.getBall().getX(),state.getBall().getY());
                state.getBall().setX(gameplay_width/2);
                state.getBall().setY(gameplay_height/2);
            }

            if (state.getScorePLAYER1() >= TARGET_SCORE) {
                state.killBall();

                time1 = System.nanoTime();

                contador = (time1-time0)/1000000;

                clearScreen();

                openGameWinPLAYER1Menu();
            }else if (state.getScorePLAYER2() >= TARGET_SCORE) {
                state.killBall();

                time1 = System.nanoTime();

                contador = (time1-time0)/1000000;

                clearScreen();

                openGameWinComputerMenu();
            }
        }

        refreshScreen();
    }

    private void readKeyboard() {
        Key k = readKeyInput();
        if(k != null) {

            switch (k.getKind()) {
                case ArrowUp -> PaddleMove(state.getPaddlePLAYER1Corpo(), DirectionPaddle.UP);
                case ArrowDown -> PaddleMove(state.getPaddlePLAYER1Corpo(), DirectionPaddle.DOWN);
            }
            if (mult) {
                switch (k.getCharacter()) {
                    case 'w' -> PaddleMove(state.getPaddlePLAYER2Corpo(), DirectionPaddle.UP);
                    case 's' -> PaddleMove(state.getPaddlePLAYER2Corpo(), DirectionPaddle.DOWN);
                }
            }
        }
    }

    private void drawMidField() {
        for(int i = 0; i <= gameplay_width; i++) {
            drawString(gameplay_width/2, i, GAMEMID_STRING, null);
            drawString(i, gameplay_height,LIMIT_STRING, null);
        }
    }

    private void drawString(int x, int y, String string, Terminal.Color fg_color) {
        screen.putString(x, y, string, fg_color, null);
    }
    private void clearStringAt(int x, int y) {
        drawString(x, y, EMPTY_STRING, null);
    }

    public void drawPaddle(){
        for(Position p : state.getPaddlePLAYER1Corpo()){
            drawString(p.getX(),p.getY(),PADDLE_STRING, Color.GREEN);
        }
    }
    public void drawPaddleMultiplayer(){
        for(Position p : state.getPaddlePLAYER2Corpo()){
            drawString(p.getX(),p.getY(),PADDLE_STRING, Color.GREEN);
        }
    }


    private void drawBall() {
        Position ball = state.getBall();
        drawString(ball.getX(), ball.getY(), BALL_STRING, Color.GREEN);
    }

    private void drawTargetScore() {
        drawString((gameplay_width/2)-8, gameplay_height + 1, "TARGET SCORE: ", Color.CYAN);
        drawString((gameplay_width/2)+6, gameplay_height + 1, Integer.toString(TARGET_SCORE), null);
    }

    private void drawScorePLAYER1() {
        int s = state.getScorePLAYER1();
        drawString(22, gameplay_height + 1, Integer.toString(s), null);
    }
    private void drawScorePLAYER2() {
        int s = state.getScorePLAYER2();
        drawString(74, gameplay_height + 1, Integer.toString(s), null);
    }

    private void drawScoreComputer() {
        int s = state.getScorePLAYER2();
        drawString(74, gameplay_height + 1, Integer.toString(s), null);
    }

    private void generateNewObstacleINV() {
        Position p = state.generateRandomObject(gameplay_width-6, 7, gameplay_height, 0);

        state.getObstacleINV().add(new Position(p.getX(), p.getY()));
        drawString(p.getX(), p.getY(), OBSTACLEINV_STRING, Color.RED);
    }

    private void generateNewObstacleCONT() {
        Position p = state.generateRandomObject(gameplay_width-6, 7, gameplay_height, 0);

        state.getObstacleCONT().add(new Position(p.getX(), p.getY()));
        drawString(p.getX(), p.getY(), OBSTACLECONT_STRING, Color.BLUE);
    }

    private void PaddleMove(LinkedList<Position> paddle, DirectionPaddle direction) {
        if((paddle.getFirst().getY() >= 1 && direction.equals(DirectionPaddle.UP)) || (paddle.getLast().getY() <= gameplay_height - 1 && direction.equals(DirectionPaddle.DOWN))) {
            for (Position p : paddle) {
                clearStringAt(p.getX(), p.getY());
                switch (direction) {
                    case UP -> p.setY(p.getY() - 1);
                    case DOWN -> p.setY(p.getY() + 1);
                }
            }
        }
    }

    private void movePaddleComputer(LinkedList<Position> paddle, DirectionPaddle direction) {
        if((paddle.getFirst().getY() >= 1 && direction.equals(DirectionPaddle.UP)) || (paddle.getLast().getY() <= gameplay_height - 1 && direction.equals(DirectionPaddle.DOWN))) {
            for (Position p : paddle) {
                clearStringAt(p.getX(), p.getY());
                switch (direction) {
                    case UP -> p.setY(p.getY() - 1);
                    case DOWN -> p.setY(p.getY() + 1);
                }
            }
        }
    }

    private void clearGameObjects() {
        clearStringAt(state.getBall().getX(), state.getBall().getY());

        for(Position p : state.getPaddlePLAYER1Corpo()) {
            clearStringAt(p.getX(), p.getY());
        }

        if(mult){
            for(Position p : state.getPaddlePLAYER2Corpo()) {
                clearStringAt(p.getX(), p.getY());
            }
        }
    }

    private Key readKeyInput() {
        return terminal.readInput();
    }

    private void refreshScreen() {
        screen.refresh();
    }

    private void clearScreen() {
        screen.clear();
    }

    private void exitGame() {
        terminal.exitPrivateMode();
    }

    //DRAW MENUS

    public void openMainMenu() {
        nivelSelecionado = GAME_LEVEL_1;
        NUMBER_OF_OBSTACLES = 3;
        PADDLE_SIZE = 10;
        TARGET_SCORE = 10;
        mult = true;
        createlevel = false;

        drawMainMenu();

        refreshScreen();

        int selected_option = opcaoMainMenu();

        if(selected_option == START_GAME) {
            clearScreen();

            state = new Game(PADDLE_SIZE, mult);

            startGame();

        }else if(selected_option == SINGLEPLAYER){
            clearScreen();
            mult = false;
            state = new Game(PADDLE_SIZE, mult);

            startGame();
        }else if(selected_option == CREATE_LEVEL){
            clearScreen();

            openCreateLevelMenu();

        }else if (selected_option== INSTRUCTIONS_GAME){
            clearScreen();
            openInstructionsMenu();
            refreshScreen();
        } else {
            exitGame();
        }
    }
    private int opcaoMainMenu() {
        Key k;

        while(true) {
            k = readKeyInput();

            if(k != null) {
                switch(k.getCharacter()) {
                    case '1':
                        nivelSelecionado = GAME_LEVEL_1;
                        WALLS = 1;
                        representacaoGraficaMainMenu(OPTION_LEVEL_1);
                        break;

                    case '2':
                        nivelSelecionado = GAME_LEVEL_2;
                        WALLS = 1;
                        representacaoGraficaMainMenu(OPTION_LEVEL_2);
                        break;

                    case '3':
                        nivelSelecionado = GAME_LEVEL_3;
                        WALLS = 1;
                        representacaoGraficaMainMenu(OPTION_LEVEL_3);
                        break;

                    case '4':
                        nivelSelecionado = GAME_LEVEL_4;
                        WALLS = 1;
                        representacaoGraficaMainMenu(OPTION_LEVEL_4);
                        break;

                    case '5':
                        nivelSelecionado = GAME_LEVEL_5;
                        WALLS = 1;
                        representacaoGraficaMainMenu(OPTION_LEVEL_5);
                        break;
                    case '6':
                        nivelSelecionado = GAME_LEVEL_5;
                        WALLS = 0;
                        representacaoGraficaMainMenu(OPTION_LEVEL_6);
                        break;
                    case 's':
                    case 'S':
                        return START_GAME;

                    case 't':
                    case 'T':
                        return SINGLEPLAYER;

                    case 'c':
                    case 'C':
                        return CREATE_LEVEL;

                    case 'i':
                    case 'I':
                        return INSTRUCTIONS_GAME;
                    case 'q':
                    case 'Q':
                        return QUIT_GAME;

                    default:
                        break;
                }
            }

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
    // Representacao grafica do menu principal
    private void representacaoGraficaMainMenu(int selected) {
        int y = 19;

        drawString(OPTION_LEVEL_1, y, "1", Color.BLUE);
        drawString(OPTION_LEVEL_2, y, "2", Color.BLUE);
        drawString(OPTION_LEVEL_3, y, "3", Color.BLUE);
        drawString(OPTION_LEVEL_4, y, "4", Color.BLUE);
        drawString(OPTION_LEVEL_5, y, "5", Color.BLUE);
        drawString(OPTION_LEVEL_6, y, "6", Color.BLUE);

        if(selected == OPTION_LEVEL_1) {
            drawString(OPTION_LEVEL_1, y, "1", Color.WHITE);
        } else if(selected == OPTION_LEVEL_2) {
            drawString(OPTION_LEVEL_2, y, "2", Color.WHITE);
        } else if(selected == OPTION_LEVEL_3) {
            drawString(OPTION_LEVEL_3, y, "3", Color.WHITE);
        } else if(selected == OPTION_LEVEL_4) {
            drawString(OPTION_LEVEL_4, y, "4", Color.WHITE);
        } else if(selected == OPTION_LEVEL_5) {
            drawString(OPTION_LEVEL_5, y, "5", Color.WHITE);
        } else if(selected == OPTION_LEVEL_6) {
            drawString(OPTION_LEVEL_6, y, "6", Color.WHITE);
        }

        refreshScreen();
    }
    // Desenha o menu principal
    private void drawMainMenu() {
        int x = 22;
        int y = 1;

        drawString(x, y, "########  ########  ###     ##  ########  ", Color.CYAN);
        drawString(x,++y,"##    ##  ##    ##  ## ##   ##  ##       ", Color.CYAN);
        drawString(x,++y,"########  ##    ##  ##  ##  ##  ##  ####  ", Color.CYAN);
        drawString(x,++y,"##        ##    ##  ##   ## ##  ##    ##  ", Color.CYAN);
        drawString(x,++y,"##        ########  ##    ####  ########  ", Color.CYAN);

        x = 21;
        drawString(x,++y,"########  #########  ###      ###  ########       ", Color.CYAN);
        drawString(x,++y,"##        ###   ###  ####    ####  ##            ", Color.CYAN);
        drawString(x,++y,"##  ####  #########  ## ##  ## ##  #####          ", Color.CYAN);
        drawString(x,++y,"##    ##  ###   ###  ##   ##   ##  ##            ", Color.CYAN);
        drawString(x,++y,"########  ###   ###  ##        ##  ########       ", Color.CYAN);

        y += 2;
        x = 25;

        drawString(x, y,   "##################################", Color.BLUE);
        drawString(x, ++y, "Press 'S' to start the game", Color.BLUE);
        drawString(x, ++y, "Press 'T' to start singleplayer game", Color.BLUE);
        drawString(x, ++y, "Press 'C' to create your own level", Color.BLUE);
        drawString(x, ++y, "Press 'I' to see game instructions", Color.BLUE);
        drawString(x, ++y, "Press 'Q' to quit the game", Color.BLUE);

        y++;

        drawString(x, ++y,  "Level:", Color.BLUE);
        drawString(OPTION_LEVEL_1, y, "1", Color.WHITE);
        drawString(OPTION_LEVEL_2, y, "2", Color.BLUE);
        drawString(OPTION_LEVEL_3, y, "3", Color.BLUE);
        drawString(OPTION_LEVEL_4, y, "4", Color.BLUE);
        drawString(OPTION_LEVEL_5, y, "5", Color.BLUE);
        drawString(OPTION_LEVEL_6, y, "6", Color.BLUE);
        drawString(x, ++y, "##################################", Color.BLUE);
    }

    private void openInstructionsMenu() {

        drawInstructionsMenu();

        refreshScreen();

        int opcaoSelecionada = opcaoInstructionsMenu();

        if(opcaoSelecionada == MAIN_MENU)
        {
            clearScreen();

            openMainMenu();
        }
        else if(opcaoSelecionada == QUIT_GAME)
        {
            exitGame();
        }
    }
    // Retornando a opcao selecionada no menu Instructions
    private int opcaoInstructionsMenu() {
        int selected  = MAIN_MENU;

        Key k;

        while(true) {
            k = readKeyInput();

            if(k != null) {
                switch(k.getKind()) {
                    case ArrowDown:
                        if(selected < QUIT_GAME)
                            selected++;
                        break;

                    case ArrowUp:
                        if(selected > MAIN_MENU)
                            selected--;
                        break;

                    case Enter:
                        return selected;

                    default:
                        break;
                }

                representacaoGraficaInstructionMenu(selected);
            }

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
    // Representacao grafica do menu Instructions
    private void representacaoGraficaInstructionMenu(int selected) {

        drawString(5, MAIN_MENU+1, "Back to main menu", Color.BLUE);
        drawString(5, QUIT_GAME+1, "Quit Game", Color.BLUE);


        if(selected == MAIN_MENU) {
            drawString(5, MAIN_MENU+1, "Back to main menu", Color.WHITE);
        } else if(selected == QUIT_GAME) {
            drawString(5, QUIT_GAME+1, "Quit Game", Color.WHITE);
        }

        refreshScreen();
    }
    // Desenha o menu INSTRUCTIONS
    private void drawInstructionsMenu() {
        int x = 5;
        int y = 1;

        drawString(x, y,   "######################################################################", Color.BLUE);
        drawString(x, ++y, "To Move the Paddle:", Color.BLUE);
        drawString(x, ++y, "- Press ArrowUp to go up (and 'w' if PLAYER 2)", Color.BLUE);
        drawString(x, ++y, "- Press ArrowDown to go down (and 's' if PLAYER 2)", Color.BLUE);
        drawString(x, ++y, "######################################################################", Color.BLUE);
        drawString(x, ++y, "The Game Rules:", Color.BLUE);
        drawString(x, ++y, "- You need to score more goals than your opponent", Color.BLUE);
        drawString(x, ++y, "- Every goal increase the score in 1", Color.BLUE);
        drawString(x, ++y, "- First Player to reach target score wins.", Color.BLUE);
        drawString(x, ++y, "- Ball start in a random direction", Color.BLUE);
        drawString(x, ++y, "- If your paddle hit the ball, it will bounce and go in other direction", Color.BLUE);
        drawString(x, ++y, "######################################################################", Color.BLUE);
        drawString(x,++y, "Singleplayer Mode: ", Color.BLUE);
        drawString(x,++y, "- You against the computer", Color.BLUE);
        drawString(x,++y, "HAVE FUN", Color.BLUE);

        y++;

        drawString(x, y, "######################################################################", Color.BLUE);
        drawString(x, ++y, "Back to main menu", Color.WHITE);
        drawString(x, ++y, "Quit Game", Color.BLUE);
        drawString(x, ++y, "######################################################################", Color.BLUE);
    }

    private void openGameWinPLAYER1Menu() {
        // Apaga todos os obstaculos
        clearGameObjects();

        drawGameWinPLAYER1Menu();

        refreshScreen();

        int opcaoSelecionada = opcaoGameWinMenu();

        if(opcaoSelecionada == RESTART_GAME) {
            clearScreen();

            state = new Game(PADDLE_SIZE, mult);

            startGame();
        } else if(opcaoSelecionada == MAIN_MENU) {
            clearScreen();

            openMainMenu();
        } else if(opcaoSelecionada == QUIT_GAME) {
            exitGame();
        }
    }

    private void openGameWinPLAYER2Menu() {
        // Apaga todos os obstaculoS
        clearGameObjects();

        drawGameWinPLAYER2Menu();

        refreshScreen();

        int opcaoSelecionada = opcaoGameWinMenu();

        if(opcaoSelecionada == RESTART_GAME) {
            clearScreen();

            state = new Game(PADDLE_SIZE, mult);

            startGame();
        } else if(opcaoSelecionada == MAIN_MENU) {
            clearScreen();

            openMainMenu();
        } else if(opcaoSelecionada == QUIT_GAME) {
            exitGame();
        }
    }

    private void openGameWinComputerMenu() {
        // Apaga todos os obstaculoS
        clearGameObjects();

        drawGameWinComputerMenu();

        refreshScreen();

        int opcaoSelecionada = opcaoGameWinMenu();

        if(opcaoSelecionada == RESTART_GAME) {
            clearScreen();

            state = new Game(PADDLE_SIZE, mult);

            startGame();
        } else if(opcaoSelecionada == MAIN_MENU) {
            clearScreen();

            openMainMenu();
        } else if(opcaoSelecionada == QUIT_GAME) {
            exitGame();
        }
    }

    private int opcaoGameWinMenu() {
        int selected  = RESTART_GAME;

        Key k;

        while(true) {
            k = readKeyInput();

            if(k != null) {
                switch(k.getKind()) {
                    case ArrowDown:
                        if(selected < QUIT_GAME)
                            selected++;

                        break;

                    case ArrowUp:
                        if(selected > RESTART_GAME)
                            selected--;

                        break;

                    case Enter:
                        return selected;

                    default:
                        break;
                }

                representacaoGraficaGameWinMenu(selected);
            }

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
    private void representacaoGraficaGameWinMenu(int selected) {
        drawString(28, RESTART_GAME, "Restart", Color.BLUE);
        drawString(28, MAIN_MENU, "Back to main menu", Color.BLUE);
        drawString(28, QUIT_GAME, "Quit Game", Color.BLUE);

        if(selected == RESTART_GAME)
        {
            drawString(28, RESTART_GAME, "Restart", Color.WHITE);
        }
        else if(selected == MAIN_MENU)
        {
            drawString(28, MAIN_MENU, "Back to main menu", Color.WHITE);
        }
        else if(selected == QUIT_GAME)
        {
            drawString(28, QUIT_GAME, "Quit Game", Color.WHITE);
        }

        refreshScreen();
    }

    private void drawGameWinPLAYER1Menu() {
        int x = 12;
        int y = 2;

        drawString(x, y,  "######  ##      #######  ### ###  ######  ######    ###", Color.CYAN);
        drawString(x, ++y,"##  ##  ##      ##   ##   ## ##   ##      ##  ##   ####", Color.CYAN);
        drawString(x, ++y,"######  ##      #######   #####   ######  ######  ## ##", Color.CYAN);
        drawString(x, ++y,"##      ##      ##   ##    ###    ##      ## ##      ##", Color.CYAN);
        drawString(x, ++y,"##      ######  ##   ##    ###    ######  ##  ##    ####", Color.CYAN);

        y++;
        x = 23;
        drawString(x, ++y,"##        ##  ###  ###     ##", Color.CYAN);
        drawString(x, ++y,"##   ##   ##  ###  ## ##   ##", Color.CYAN);
        drawString(x, ++y,"## ##  ## ##  ###  ##  ##  ##", Color.CYAN);
        drawString(x, ++y,"####    ####  ###  ##   ## ##", Color.CYAN);
        drawString(x, ++y,"###      ###  ###  ##    ####", Color.CYAN);

        y++;
        x = 28;

        drawString(x, ++y, "####################", Color.BLUE);
        drawString(x, ++y, "Restart", Color.WHITE);
        drawString(x, ++y, "Back to main menu", Color.BLUE);
        drawString(x, ++y, "Quit Game", Color.BLUE);
        drawString(x, ++y, "####################", Color.BLUE);
    }

    private void drawGameWinPLAYER2Menu() {
        int x = 12;
        int y = 2;

        drawString(x, y,  "######  ##      #######  ### ###  ######  ######   #####", Color.CYAN);
        drawString(x, ++y,"##  ##  ##      ##   ##   ## ##   ##      ##  ##  ##  ##", Color.CYAN);
        drawString(x, ++y,"######  ##      #######   #####   ######  ######     ##", Color.CYAN);
        drawString(x, ++y,"##      ##      ##   ##    ###    ##      ## ##     ##", Color.CYAN);
        drawString(x, ++y,"##      ######  ##   ##    ###    ######  ##  ##   ######", Color.CYAN);

        y++;
        x = 23;
        drawString(x, ++y,"##        ##  ###  ###     ##", Color.CYAN);
        drawString(x, ++y,"##   ##   ##  ###  ## ##   ##", Color.CYAN);
        drawString(x, ++y,"## ##  ## ##  ###  ##  ##  ##", Color.CYAN);
        drawString(x, ++y,"####    ####  ###  ##   ## ##", Color.CYAN);
        drawString(x, ++y,"###      ###  ###  ##    ####", Color.CYAN);

        y++;
        x = 28;

        drawString(x, ++y, "####################", Color.BLUE);
        drawString(x, ++y, "Restart", Color.WHITE);
        drawString(x, ++y, "Back to main menu", Color.BLUE);
        drawString(x, ++y, "Quit Game", Color.BLUE);
        drawString(x, ++y, "####################", Color.BLUE);
    }

    private void drawGameWinComputerMenu() {
        int x = 9;
        int y = 2;

        drawString(x, y,  "######  ######  ### ###  ######  ##  ##  ######  ######  ######", Color.CYAN);
        drawString(x, ++y,"##      ##  ##  ### ###  ##  ##  ##  ##    ##    ##      ##  ##", Color.CYAN);
        drawString(x, ++y,"##      ##  ##  ## # ##  ######  ##  ##    ##    ######  ######", Color.CYAN);
        drawString(x, ++y,"##      ##  ##  ##   ##  ##      ##  ##    ##    ##      ## ## ", Color.CYAN);
        drawString(x, ++y,"######  ######  ##   ##  ##      ######    ##    ######  ##  ##", Color.CYAN);

        y++;
        x = 23;
        drawString(x, ++y,"##        ##  ###  ###     ##", Color.CYAN);
        drawString(x, ++y,"##   ##   ##  ###  ## ##   ##", Color.CYAN);
        drawString(x, ++y,"## ##  ## ##  ###  ##  ##  ##", Color.CYAN);
        drawString(x, ++y,"####    ####  ###  ##   ## ##", Color.CYAN);
        drawString(x, ++y,"###      ###  ###  ##    ####", Color.CYAN);

        y++;
        x = 28;

        drawString(x, ++y, "####################", Color.BLUE);
        drawString(x, ++y, "Restart", Color.WHITE);
        drawString(x, ++y, "Back to main menu", Color.BLUE);
        drawString(x, ++y, "Quit Game", Color.BLUE);
        drawString(x, ++y, "####################", Color.BLUE);
    }

    private void openCreateLevelMenu() {
        NUMBER_OF_OBSTACLES = 5;
        DIFFICULTY = 3;
        PADDLE_SIZE = 3;
        WALLS = 0;
        TARGET_SCORE = 20;
        createlevel = true;

        drawCreateLevelMenu();

        refreshScreen();

        int opcaoSelecionada = opcaoCreateLevelMenu();

        if(opcaoSelecionada == CHOOSE_START_GAME) {
            clearScreen();

            switch (DIFFICULTY) {
                case 1 -> nivelSelecionado = GAME_LEVEL_1;
                case 2 -> nivelSelecionado = GAME_LEVEL_2;
                case 3 -> nivelSelecionado = GAME_LEVEL_3;
                case 4 -> nivelSelecionado = GAME_LEVEL_4;
                case 5 -> nivelSelecionado = GAME_LEVEL_5;
                default -> {
                }
            }

            state = new Game(PADDLE_SIZE,mult);
            startGame();

        } else if(opcaoSelecionada == MAIN_MENU) {
            clearScreen();

            openMainMenu();
        }
        else if(opcaoSelecionada == QUIT_GAME) {
            exitGame();
        }
    }
    // Retornando a opcao selecionada no menu CREATE LEVEL
    private int opcaoCreateLevelMenu() {
        int selected  = CHOOSE_NUMBER_OF_OBSTACLES;

        Key k;

        while(true) {
            k = readKeyInput();

            if(k != null) {
                switch(k.getKind()) {
                    case ArrowDown:
                        if(selected < QUIT_GAME)
                            selected++;

                        break;

                    case ArrowUp:
                        if(selected > CHOOSE_NUMBER_OF_OBSTACLES)
                            selected--;

                        break;
                    case ArrowRight:
                        if(selected == CHOOSE_NUMBER_OF_OBSTACLES && NUMBER_OF_OBSTACLES < 50) {
                            NUMBER_OF_OBSTACLES++;
                        } else if(selected == CHOOSE_DIFFICULTY && DIFFICULTY < 5) {
                            DIFFICULTY++;
                        } else if(selected == CHOOSE_WALLS && WALLS < 1) {
                            WALLS++;
                        }else if(selected == CHOOSE_MULT && mult) {
                            mult=false;
                        }else if(selected == CHOOSE_PADDLE_SIZE && PADDLE_SIZE < 10) {
                            PADDLE_SIZE++;
                        }else if (selected == CHOOSE_TARGET_SCORE && TARGET_SCORE < 50) {
                            TARGET_SCORE++;
                        }
                        break;
                    case ArrowLeft:
                        if(selected == CHOOSE_NUMBER_OF_OBSTACLES && NUMBER_OF_OBSTACLES > 0) {
                            NUMBER_OF_OBSTACLES--;
                        } else if(selected == CHOOSE_DIFFICULTY && DIFFICULTY > 1) {
                            DIFFICULTY--;
                        } else if(selected == CHOOSE_WALLS && WALLS > 0) {
                            WALLS--;
                        } else if(selected == CHOOSE_MULT && !mult) {
                            mult=true;
                        }else if(selected == CHOOSE_PADDLE_SIZE && PADDLE_SIZE > 1) {
                            PADDLE_SIZE--;
                        }else if (selected == CHOOSE_TARGET_SCORE && TARGET_SCORE > 1) {
                            TARGET_SCORE--;
                        }
                        break;
                    case Enter:
                        if(selected == QUIT_GAME || selected == CHOOSE_START_GAME || selected == MAIN_MENU) {
                            return selected;
                        }
                        else break;
                    default:
                        break;
                }

                representacaoGraficaCreateLevelMenu(selected);
            }

            try {
                Thread.sleep(100);
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }
    // Representacao grafica do menu CREATE LEVEL
    private void representacaoGraficaCreateLevelMenu(int selected) {

        drawString(5, 3, "CREATE YOUR LEVEL:", Color.BLUE);
        drawString(5, 4, "- Number of Obstacles: " + NUMBER_OF_OBSTACLES, Color.BLUE);
        drawString(5, 5, "- Difficulty: " + DIFFICULTY, Color.BLUE);
        drawString(5, 6, "- Go through Walls: Yes No", Color.BLUE);
        drawString(5, 7, "- 2 Players: Yes No", Color.BLUE);
        drawString(5, 8, "- Paddles size: " + PADDLE_SIZE, Color.BLUE);
        drawString(5, 9, "- Target Score: " + TARGET_SCORE, Color.BLUE);
        drawString(3, 12, "NOTE:", Color.BLACK);
        drawString(3, 13, "Obstacle '$' makes the ball go on the same direction but in a different way", Color.BLACK);
        drawString(3, 14, "Obstacle 'X' makes the ball go on the opposite direction and a different way", Color.BLACK);
        drawString(5, 18, "Start Game", Color.BLUE);
        drawString(5, MAIN_MENU + 3, "Back to main menu", Color.BLUE);
        drawString(5, QUIT_GAME + 3, "Quit Game", Color.BLUE);

        if (selected == CHOOSE_NUMBER_OF_OBSTACLES) {
            drawString(5, 4, "- Number of Obstacles: " + NUMBER_OF_OBSTACLES + "  ", Color.WHITE);
            drawString(3, 12, "NOTE:", Color.WHITE);
            drawString(3, 13, "Obstacle '$' makes the ball go on the same direction but in a different way", Color.WHITE);
            drawString(3, 14, "Obstacle 'X' makes the ball go on the opposite direction and a different way", Color.WHITE);
        } else if (selected == CHOOSE_DIFFICULTY) {
            drawString(5, 5, "- Difficulty: " + DIFFICULTY + "  ", Color.WHITE);
        } else if (selected == CHOOSE_WALLS) {
            if (WALLS == 0) {
                drawString(5, 6, "- Go through Walls: Yes", Color.WHITE);
                drawString(29, 6, "No", Color.BLUE);
            } else if (WALLS == 1) {
                drawString(5, 6, "- Go through Walls: ", Color.WHITE);
                drawString(25, 6, "Yes", Color.BLUE);
                drawString(29, 6, "No", Color.WHITE);
            }
        } else if (selected == CHOOSE_MULT) {
            if (mult) {
                drawString(5, 7, "- 2 Players: Yes", Color.WHITE);
                drawString(22, 7, "No", Color.BLUE);
            } else {
                drawString(5, 7, "- 2 Players: ", Color.WHITE);
                drawString(18, 7, "Yes", Color.BLUE);
                drawString(22, 7, "No", Color.WHITE);
            }
        }else if(selected == CHOOSE_PADDLE_SIZE) {
            drawString(5, 8, "- Paddles size: " + PADDLE_SIZE + "  ", Color.WHITE);
        } else if (selected == CHOOSE_TARGET_SCORE) {
            drawString(5, 9, "- Target Score: " + TARGET_SCORE + "  ", Color.WHITE);
        } else if (selected == CHOOSE_START_GAME) {
            drawString(5, 18, "Start Game", Color.WHITE);
        } else if(selected == MAIN_MENU) {
            drawString(5, MAIN_MENU+3, "Back to main menu", Color.WHITE);
        } else if(selected == QUIT_GAME) {
            drawString(5, QUIT_GAME+3, "Quit Game", Color.WHITE);
        }

        refreshScreen();
    }
    // Desenha o menu CREATE LEVEL
    private void drawCreateLevelMenu() {
        int x = 5;
        int y = 2;

        drawString(x, y,   "######################################################################", Color.BLUE);
        drawString(x, ++y, "CREATE YOUR LEVEL:", Color.BLUE);
        drawString(x, ++y, "- Number of Obstacles: " + NUMBER_OF_OBSTACLES, Color.WHITE);
        drawString(x, ++y, "- Difficulty: " + DIFFICULTY, Color.BLUE);
        drawString(x, ++y, "- Go through Walls: Yes No", Color.BLUE);
        drawString(x, ++y, "- 2 Players: Yes No", Color.BLUE);
        drawString(x, ++y, "- Paddles size: " + PADDLE_SIZE, Color.BLUE);
        drawString(x, ++y, "- Target Score: " + TARGET_SCORE, Color.BLUE);
        drawString(x, ++y, "######################################################################", Color.BLUE);

        drawString(3, 12, "NOTE:", Color.WHITE);
        drawString(3, 13, "Obstacle '$' makes the ball go on the same direction but in a different way", Color.WHITE);
        drawString(3, 14, "Obstacle 'X' makes the ball go on the opposite direction and a different way", Color.WHITE);
        
        y += 7;

        drawString(x, y, "######################################################################", Color.BLUE);
        drawString(x, ++y,"Start Game", Color.BLUE);
        drawString(x, ++y, "Back to main menu", Color.BLUE);
        drawString(x, ++y, "Quit Game", Color.BLUE);
        drawString(x, ++y, "######################################################################", Color.BLUE);
    }
}
