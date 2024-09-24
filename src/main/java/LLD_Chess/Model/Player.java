package LLD_Chess.Model;

public class Player {
    public String name;
    public PlayingColor color;

    public Player(String name, PlayingColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public PlayingColor getColor() {
        return color;
    }
}
