package LLD_Game2048;

import java.util.Scanner;

public class Game2048 {
    private Board board;

    public Game2048() {
        board = new Board();
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            board.printBoard();
            System.out.println("Press 0-Left, 1-Right, 2-Up, 3-Down: ");
            int move = scanner.nextInt();
            boolean moved = false;
            switch (move) {
                case 0 -> moved = board.move(Move.LEFT);
                case 1 -> moved = board.move(Move.RIGHT);
                case 2 -> moved = board.move(Move.UP);
                case 3 -> moved = board.move(Move.DOWN);
            }
            if (!moved) {
                System.out.println("Invalid move! Try again.");
                continue;
            }
            if (board.checkWin()) {
                board.printBoard();
                System.out.println("Congratulations, you won!");
                break;
            }
            if (board.checkGameOver()) {
                board.printBoard();
                System.out.println("Game over!");
                break;
            }
        }
        scanner.close();
    }
}
