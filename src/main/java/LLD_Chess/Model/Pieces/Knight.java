package LLD_Chess.Model.Pieces;

import LLD_Chess.Board;
import LLD_Chess.Model.Move;
import LLD_Chess.Model.PlayingColor;
import LLD_Chess.Model.Position;

import java.util.ArrayList;
import java.util.List;

import static LLD_Chess.Model.PlayingColor.WHITE;

public class Knight extends Piece {

    public Knight(PlayingColor color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

        PiecesUtils piecesUtils = new PiecesUtils(board);

        Position start = move.start;
        Position end = move.end;

        // Ensure the move stays within the bounds of the board
        if (!piecesUtils.isWithinBounds(end)) {
            return false;
        }

        // Calculate the differences in x and y positions
        int dx = Math.abs(end.x - start.x);
        int dy = Math.abs(end.y - start.y);

        // Check for "L" shape movement
        if ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) {
            // Check if the destination is either empty or occupied by an opponent's piece
            return piecesUtils.isSquareEmpty(end) || piecesUtils.isSquareOccupiedByOpponent(end, this.color);
        }

        return false;
    }

    @Override
    public List<Move> getAllPossibleMoves(Board board, Position start) {
        List<Move> moves = new ArrayList<>();
        PiecesUtils piecesUtils = new PiecesUtils(board);

        // Possible knight moves in "L" shape
        int[][] directions = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] direction : directions) {
            int x = start.x + direction[0];
            int y = start.y + direction[1];
            Position end = new Position(x, y);
            if (piecesUtils.isWithinBounds(end) && (piecesUtils.isSquareEmpty(end) || piecesUtils.isSquareOccupiedByOpponent(end, this.color))) {
                moves.add(new Move(start, end));
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return (color == WHITE) ? "WN" : "BN";
    }
}
