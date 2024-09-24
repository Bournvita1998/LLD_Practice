package LLD_Chess.Model.Pieces;

import LLD_Chess.Board;
import LLD_Chess.Model.Move;
import LLD_Chess.Model.PlayingColor;
import LLD_Chess.Model.Position;

import java.util.ArrayList;
import java.util.List;

import static LLD_Chess.Model.PlayingColor.WHITE;

public class Pawn extends Piece {

    public Pawn(PlayingColor color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Move move, Board board) {

        PiecesUtils piecesUtils = new PiecesUtils(board);

        Position start = move.start;
        Position end = move.end;

        if (!piecesUtils.isWithinBounds(end)) {
            return false;
        }

        int direction = (this.color == WHITE) ? 1 : -1;

        // Normal move (one square forward)
        if (start.x + direction == end.x && start.y == end.y) {
            return piecesUtils.isSquareEmpty(end);
        }

        // First move (two squares forward)
        if ((start.x == 1 && this.color == WHITE) || (start.x == 6 && this.color == PlayingColor.BLACK)) {
            if (start.x + 2 * direction == end.x && start.y == end.y) {
                return piecesUtils.isSquareEmpty(end) && piecesUtils.isSquareEmpty(new Position(start.x + direction, start.y));
            }
        }

        // Capture move (one square diagonally)
        if (start.x + direction == end.x && Math.abs(start.y - end.y) == 1) {
            return piecesUtils.isSquareOccupiedByOpponent(end, this.color);
        }

        return false;
    }

    @Override
    public List<Move> getAllPossibleMoves(Board board, Position start) {
        List<Move> moves = new ArrayList<>();
        PiecesUtils piecesUtils = new PiecesUtils(board);

        int direction = (this.color == WHITE) ? 1 : -1;

        // Normal move (one square forward)
        Position oneStepForward = new Position(start.x + direction, start.y);
        if (piecesUtils.isWithinBounds(oneStepForward) && piecesUtils.isSquareEmpty(oneStepForward)) {
            moves.add(new Move(start, oneStepForward));
        }

        // First move (two squares forward)
        if ((start.x == 1 && this.color == WHITE) || (start.x == 6 && this.color == PlayingColor.BLACK)) {
            Position twoStepsForward = new Position(start.x + 2 * direction, start.y);
            if (piecesUtils.isWithinBounds(twoStepsForward) && piecesUtils.isSquareEmpty(twoStepsForward) && piecesUtils.isSquareEmpty(oneStepForward)) {
                moves.add(new Move(start, twoStepsForward));
            }
        }

        // Capture moves (one square diagonally)
        Position captureLeft = new Position(start.x + direction, start.y - 1);
        Position captureRight = new Position(start.x + direction, start.y + 1);
        if (piecesUtils.isWithinBounds(captureLeft) && piecesUtils.isSquareOccupiedByOpponent(captureLeft, this.color)) {
            moves.add(new Move(start, captureLeft));
        }
        if (piecesUtils.isWithinBounds(captureRight) && piecesUtils.isSquareOccupiedByOpponent(captureRight, this.color)) {
            moves.add(new Move(start, captureRight));
        }

        return moves;
    }

    @Override
    public String toString() {
        return (color == WHITE) ? "WP" : "BP";
    }
}
