package LLD_Game2048;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private static final int SIZE = 4;
    private Tile[][] tiles;
    private Random random;

    public Board() {
        tiles = new Tile[SIZE][SIZE];
        random = new Random();
        initializeBoard();
    }

    private void initializeBoard() {
        addRandomTile();
        addRandomTile();
    }

    private void addRandomTile() {
        List<int[]> emptyTiles = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (tiles[i][j] == null) {
                    emptyTiles.add(new int[]{i, j});
                }
            }
        }
        if (!emptyTiles.isEmpty()) {
            int[] tile = emptyTiles.get(random.nextInt(emptyTiles.size()));
            tiles[tile[0]][tile[1]] = new Tile(random.nextDouble() < 0.9 ? 2 : 4);
        }
    }

    public void printBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (tiles[i][j] == null) {
                    System.out.print("- ");
                } else {
                    System.out.print(tiles[i][j].getValue() + " ");
                }
            }
            System.out.println();
        }
    }

    public boolean move(Move move) {
        boolean moved = false;
        switch (move) {
            case LEFT -> moved = moveLeft();
            case RIGHT -> moved = moveRight();
            case UP -> moved = moveUp();
            case DOWN -> moved = moveDown();
        }
        if (moved) {
            addRandomTile();
        }
        return moved;
    }

    private boolean moveLeft() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            Tile[] newRow = new Tile[SIZE];
            int newIndex = 0;
            boolean merged = false;
            for (int j = 0; j < SIZE; j++) {
                if (tiles[i][j] != null) {
                    if (newIndex > 0 && newRow[newIndex - 1].canMergeWith(tiles[i][j]) && !merged) {
                        newRow[newIndex - 1].mergeWith(tiles[i][j]);
                        merged = true;
                    } else {
                        newRow[newIndex++] = tiles[i][j];
                        merged = false;
                    }
                }
            }
            if (!moved) {
                for (int j = 0; j < SIZE; j++) {
                    if (tiles[i][j] != newRow[j]) {
                        moved = true;
                        break;
                    }
                }
            }
            tiles[i] = newRow;
        }
        return moved;
    }

    private boolean moveRight() {
        boolean moved = false;
        for (int i = 0; i < SIZE; i++) {
            Tile[] newRow = new Tile[SIZE];
            int newIndex = SIZE - 1;
            boolean merged = false;
            for (int j = SIZE - 1; j >= 0; j--) {
                if (tiles[i][j] != null) {
                    if (newIndex < SIZE - 1 && newRow[newIndex + 1].canMergeWith(tiles[i][j]) && !merged) {
                        newRow[newIndex + 1].mergeWith(tiles[i][j]);
                        merged = true;
                    } else {
                        newRow[newIndex--] = tiles[i][j];
                        merged = false;
                    }
                }
            }
            if (!moved) {
                for (int j = 0; j < SIZE; j++) {
                    if (tiles[i][j] != newRow[j]) {
                        moved = true;
                        break;
                    }
                }
            }
            tiles[i] = newRow;
        }
        return moved;
    }

    private boolean moveUp() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            Tile[] newColumn = new Tile[SIZE];
            int newIndex = 0;
            boolean merged = false;
            for (int i = 0; i < SIZE; i++) {
                if (tiles[i][j] != null) {
                    if (newIndex > 0 && newColumn[newIndex - 1].canMergeWith(tiles[i][j]) && !merged) {
                        newColumn[newIndex - 1].mergeWith(tiles[i][j]);
                        merged = true;
                    } else {
                        newColumn[newIndex++] = tiles[i][j];
                        merged = false;
                    }
                }
            }
            if (!moved) {
                for (int i = 0; i < SIZE; i++) {
                    if (tiles[i][j] != newColumn[i]) {
                        moved = true;
                        break;
                    }
                }
            }
            for (int i = 0; i < SIZE; i++) {
                tiles[i][j] = newColumn[i];
            }
        }
        return moved;
    }

    private boolean moveDown() {
        boolean moved = false;
        for (int j = 0; j < SIZE; j++) {
            Tile[] newColumn = new Tile[SIZE];
            int newIndex = SIZE - 1;
            boolean merged = false;
            for (int i = SIZE - 1; i >= 0; i--) {
                if (tiles[i][j] != null) {
                    if (newIndex < SIZE - 1 && newColumn[newIndex + 1].canMergeWith(tiles[i][j]) && !merged) {
                        newColumn[newIndex + 1].mergeWith(tiles[i][j]);
                        merged = true;
                    } else {
                        newColumn[newIndex--] = tiles[i][j];
                        merged = false;
                    }
                }
            }
            if (!moved) {
                for (int i = 0; i < SIZE; i++) {
                    if (tiles[i][j] != newColumn[i]) {
                        moved = true;
                        break;
                    }
                }
            }
            for (int i = 0; i < SIZE; i++) {
                tiles[i][j] = newColumn[i];
            }
        }
        return moved;
    }

    public boolean checkWin() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (tiles[i][j] != null && tiles[i][j].getValue() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkGameOver() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (tiles[i][j] == null) {
                    return false;
                }
                if (i > 0 && tiles[i][j].canMergeWith(tiles[i - 1][j])) {
                    return false;
                }
                if (j > 0 && tiles[i][j].canMergeWith(tiles[i][j - 1])) {
                    return false;
                }
                if (i < SIZE - 1 && tiles[i][j].canMergeWith(tiles[i + 1][j])) {
                    return false;
                }
                if (j < SIZE - 1 && tiles[i][j].canMergeWith(tiles[i][j + 1])) {
                    return false;
                }
            }
        }
        return true;
    }
}
