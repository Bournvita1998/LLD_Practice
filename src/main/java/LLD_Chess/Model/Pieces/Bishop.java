package LLD_Chess.Model.Pieces;

import LLD_Chess.Board;
import LLD_Chess.Model.Move;
import LLD_Chess.Model.PlayingColor;
import LLD_Chess.Model.Position;

import java.util.ArrayList;
import java.util.List;

import static LLD_Chess.Model.PlayingColor.WHITE;

public class Bishop extends Piece {

    public Bishop(PlayingColor color) {
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

        // Check for valid bishop movement (diagonal move)
        if (Math.abs(start.x - end.x) == Math.abs(start.y - end.y)) {
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

        // Check all four diagonal directions

        // Top-right
        int x = start.x + 1;
        int y = start.y + 1;
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
            x++;
            y++;
        }

        // Top-left
        x = start.x + 1;
        y = start.y - 1;
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
            x++;
            y--;
        }

        // Bottom-right
        x = start.x - 1;
        y = start.y + 1;
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
            x--;
            y++;
        }

        // Bottom-left
        x = start.x - 1;
        y = start.y - 1;
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
            x--;
            y--;
        }

        return moves;
    }

    @Override
    public String toString() {
        return (color == WHITE) ? "WB" : "BB";
    }
}
