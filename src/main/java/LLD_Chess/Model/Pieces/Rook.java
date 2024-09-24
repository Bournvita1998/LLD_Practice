package LLD_Chess.Model.Pieces;

import LLD_Chess.Board;
import LLD_Chess.Model.Move;
import LLD_Chess.Model.PlayingColor;
import LLD_Chess.Model.Position;

import java.util.ArrayList;
import java.util.List;

import static LLD_Chess.Model.PlayingColor.WHITE;

public class Rook extends Piece {

    public Rook(PlayingColor color) {
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

        // Check for valid rook movement (either same row or same column)
        if (start.x == end.x || start.y == end.y) {
            if (piecesUtils.isPathClear(start, end, board)) {
                // Check if the destination is either empty or occupied by an opponent's piece
                return piecesUtils.isSquareEmpty(end) || piecesUtils.isSquareOccupiedByOpponent(end, this.color);
            }
        }

        return false;
    }

    @Override
    public List<Move> getAllPossibleMoves(Board board, Position start) {
        List<Move> moves = new ArrayList<>();
        PiecesUtils piecesUtils = new PiecesUtils(board);

        // Possible directions the Rook can move (straight lines along rows and columns)
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for (int i = 0; i < dx.length; i++) {
            int x = start.x + dx[i];
            int y = start.y + dy[i];
            while (piecesUtils.isWithinBounds(new Position(x, y))) {
                Position end = new Position(x, y);
                if (piecesUtils.isSquareEmpty(end)) {
                    moves.add(new Move(start, end));
                } else if (piecesUtils.isSquareOccupiedByOpponent(end, this.color)) {
                    moves.add(new Move(start, end));
                    break;
                } else {
                    break;
                }
                x += dx[i];
                y += dy[i];
            }
        }

        return moves;
    }

    @Override
    public String toString() {
        return (color == WHITE) ? "WR" : "BR";
    }
}
