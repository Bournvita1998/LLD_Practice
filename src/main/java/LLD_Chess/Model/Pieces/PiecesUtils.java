package LLD_Chess.Model.Pieces;

import LLD_Chess.Board;
import LLD_Chess.Model.PlayingColor;
import LLD_Chess.Model.Position;

public class PiecesUtils {

    Board board;

    public PiecesUtils(Board board) {
        this.board = board;
    }

    public boolean isWithinBounds(Position position) {
        return position.x >= 0 && position.x < 8 && position.y >= 0 && position.y < 8;
    }

    public boolean isPathClear(Position start, Position end, Board board) {
        int xDirection = Integer.compare(end.x, start.x);
        int yDirection = Integer.compare(end.y, start.y);

        int x = start.x + xDirection;
        int y = start.y + yDirection;

        while (x != end.x || y != end.y) {
            if (!isSquareEmpty(new Position(x, y))) {
                return false;
            }
            if (xDirection != 0) x += xDirection;
            if (yDirection != 0) y += yDirection;

        }
        return true;
    }

    public boolean isSquareEmpty(Position position) {
        return board.getPieceAt(position) == null;
    }

    public boolean isSquareOccupiedByOpponent(Position position, PlayingColor color) {
        Piece piece = board.getPieceAt(position);
        return piece != null && piece.getColor() != color;
    }
}
