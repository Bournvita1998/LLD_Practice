package LLD_Chess.Model.Pieces;

import LLD_Chess.Board;
import LLD_Chess.Model.Move;
import LLD_Chess.Model.PlayingColor;
import LLD_Chess.Model.Position;

import java.util.ArrayList;
import java.util.List;

import static LLD_Chess.Model.PlayingColor.WHITE;

public class King extends Piece {

    public King(PlayingColor color) {
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

        // Check for valid king movement (one square in any direction)
        int dx = Math.abs(end.x - start.x);
        int dy = Math.abs(end.y - start.y);

        if ((dx <= 1 && dy <= 1) && (dx + dy != 0)) {
            // Check if the destination is either empty or occupied by an opponent's piece
            return piecesUtils.isSquareEmpty(end) || piecesUtils.isSquareOccupiedByOpponent(end, this.color);
        }

        // Castling logic can be added here as an advanced feature

        return false;
    }

    @Override
    public List<Move> getAllPossibleMoves(Board board, Position start) {
        List<Move> moves = new ArrayList<>();
        PiecesUtils piecesUtils = new PiecesUtils(board);

        // Possible directions the King can move
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < dx.length; i++) {
            Position end = new Position(start.x + dx[i], start.y + dy[i]);
            if (piecesUtils.isWithinBounds(end) && (piecesUtils.isSquareEmpty(end) || piecesUtils.isSquareOccupiedByOpponent(end, this.color))) {
                moves.add(new Move(start, end));
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return (color == WHITE) ? "WK" : "BK";
    }
}
