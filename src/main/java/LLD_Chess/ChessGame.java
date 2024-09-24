package LLD_Chess;

import LLD_Chess.Model.Move;
import LLD_Chess.Model.Pieces.King;
import LLD_Chess.Model.Pieces.Piece;
import LLD_Chess.Model.Player;
import LLD_Chess.Model.PlayingColor;
import LLD_Chess.Model.Position;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static LLD_Chess.Model.PlayingColor.BLACK;
import static LLD_Chess.Model.PlayingColor.WHITE;

public class ChessGame {

    private Board board;
    private Deque<Player> playersList = new LinkedList<>();
    private Player winner;

    public ChessGame() {
        initializeGame();
    }

    public void initializeGame() {
        board = new Board();
        addPlayers();
        winner = null;
    }

    private void addPlayers() {
        Player player1 = new Player("p1", WHITE);
        Player player2 = new Player("p2", BLACK);
        playersList.add(player1);
        playersList.add(player2);
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (winner == null) {
            Player currentPlayer = playersList.pop();
            System.out.println("Current player: " + currentPlayer.getName());
            board.printBoard(); // Print the current state of the board

            // Get the move from the current player
            System.out.println("Enter your start and end move (e.g., 'e2 e4'):");
            String moveInput = scanner.nextLine();

            if (!processMove(moveInput, currentPlayer)) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            // Check if the move results in checkmate or other win condition
            if (isGameOver()) {
                winner = currentPlayer;
            } else {
                playersList.add(currentPlayer);
            }
        }
        scanner.close();
        System.out.println("WINNER IS: " + winner.getName());
    }

    private boolean processMove(String moveInput, Player currentPlayer) {
        // Process the move string (e.g., "e2 e4") and update the board
        String[] positions = moveInput.split(" ");
        if (positions.length != 2) {
            return false;
        }

        Position start = parsePosition(positions[0]);
        Position end = parsePosition(positions[1]);

        if (start == null || end == null) {
            return false;
        }

        Move move = new Move(start, end);
        Piece piece = board.getPieceAt(start);

        if (piece == null || piece.getColor() != currentPlayer.getColor()) {
            return false;
        }

        if (piece.isValidMove(move, board)) {
            // Capture any piece at the end position (not needed in this context since it will be overwritten)
            // Move the piece from the start position
            board.setPieceAt(start, null);
            board.setPieceAt(end, piece);

            return true;
        }

        return false;
    }

    private Position parsePosition(String pos) {
        // Convert board position notation (e.g., "e2") to Position object
        if (pos.length() != 2) {
            return null;
        }

        int x = pos.charAt(0) - 'a';
        int y = Character.getNumericValue(pos.charAt(1)) - 1;

        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            return null;
        }

        return new Position(x, y);
    }

    private boolean isGameOver() {
        Player currentPlayer = playersList.peek();
        PlayingColor currentColor = currentPlayer.getColor();

        // Check if the king is in check
        boolean isInCheck = isKingInCheck(currentColor);

        // Check if there are any legal moves available
        boolean hasLegalMoves = hasLegalMoves(currentColor);

        // Checkmate: The king is in check and there are no legal moves
        if (isInCheck && !hasLegalMoves) {
            return true;
        }

        // Stalemate: The king is not in check but there are no legal moves
        if (!isInCheck && !hasLegalMoves) {
            return true;
        }

        return false;
    }

    private boolean isKingInCheck(PlayingColor color) {
        // Find the king's position
        Position kingPosition = findKingPosition(color);

        // Check if any opponent's piece can move to the king's position
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board.getPieceAt(new Position(x, y));
                if (piece != null && piece.getColor() != color) {
                    if (piece.isValidMove(new Move(new Position(x, y), kingPosition), board)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private Position findKingPosition(PlayingColor color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board.getPieceAt(new Position(x, y));
                if (piece instanceof King && piece.getColor() == color) {
                    return new Position(x, y);
                }
            }
        }
        return null;
    }

    private boolean hasLegalMoves(PlayingColor color) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Piece piece = board.getPieceAt(new Position(x, y));
                if (piece != null && piece.getColor() == color) {
                    Position possibleStart = new Position(x, y);
                    List<Move> possibleMoves = piece.getAllPossibleMoves(board, possibleStart);
                    for (Move move : possibleMoves) {
                        if (piece.isValidMove(move, board)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
