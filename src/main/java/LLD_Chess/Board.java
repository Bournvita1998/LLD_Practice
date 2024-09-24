package LLD_Chess;

import LLD_Chess.Model.Pieces.*;
import LLD_Chess.Model.Position;

import static LLD_Chess.Model.PlayingColor.BLACK;
import static LLD_Chess.Model.PlayingColor.WHITE;

public class Board {
    private Piece[][] board;

    public Board() {
        board = new Piece[8][8];
        setupBoard();
    }

    public Piece getPieceAt(Position position) {
        return board[position.x][position.y];
    }

    public void setPieceAt(Position position, Piece piece) {
        board[position.x][position.y] = piece;
    }

    private void setupBoard() {
        // Initialize pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(WHITE);
            board[6][i] = new Pawn(BLACK);
        }

        // Initialize rooks
        board[0][0] = new Rook(WHITE);
        board[0][7] = new Rook(WHITE);
        board[7][0] = new Rook(BLACK);
        board[7][7] = new Rook(BLACK);

        // Initialize knights
        board[0][1] = new Knight(WHITE);
        board[0][6] = new Knight(WHITE);
        board[7][1] = new Knight(BLACK);
        board[7][6] = new Knight(BLACK);

        // Initialize bishops
        board[0][2] = new Bishop(WHITE);
        board[0][5] = new Bishop(WHITE);
        board[7][2] = new Bishop(BLACK);
        board[7][5] = new Bishop(BLACK);

        // Initialize queens
        board[0][3] = new Queen(WHITE);
        board[7][3] = new Queen(BLACK);

        // Initialize kings
        board[0][4] = new King(WHITE);
        board[7][4] = new King(BLACK);
    }

    public void printBoard() {
        for (int x = 7; x >= 0; x--) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board[x][y];
                if (piece != null) {
                    System.out.print(piece.toString() + " ");
                } else {
                    System.out.print("-- ");
                }
            }
            System.out.println();
        }
    }
}
