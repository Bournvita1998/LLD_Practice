package LLD_Chess.Model.Pieces;

import LLD_Chess.Board;
import LLD_Chess.Model.Move;
import LLD_Chess.Model.PlayingColor;
import LLD_Chess.Model.Position;

import java.util.List;

public abstract class Piece {
    protected PlayingColor color;

    public Piece(PlayingColor color) {
        this.color = color;
    }

    public PlayingColor getColor() {
        return color;
    }

    public abstract boolean isValidMove(Move move, Board board);

    public abstract List<Move> getAllPossibleMoves(Board board, Position start);

}
