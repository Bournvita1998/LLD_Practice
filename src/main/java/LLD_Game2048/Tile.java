package LLD_Game2048;

public class Tile {
    private int value;

    public Tile(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean canMergeWith(Tile other) {
        return other != null && this.value == other.getValue();
    }

    public void mergeWith(Tile other) {
        if (canMergeWith(other)) {
            this.value *= 2;
            other.setValue(0);
        }
    }
}

