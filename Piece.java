public class Piece {
    private final int colour;
    private Position position;
    private boolean king;

    public Piece(int colour, Position position) {
        this.colour = colour;
        this.position = position;
        this.king = false;
    }

    public void promote() {
        this.king = true;
    }

    public int getColour() {
        return this.colour;
    }

    public boolean isKing() {
        return this.king;
    }

    public Position getPosition() {
        return this.position;
    }
    
    public void setPosition(Position position) {
        this.position = position;
    }

    public int getX() {
        return this.position.getX();
    }

    public int getY() {
        return this.position.getY();
    }

    @Override
    public String toString(){
        return "Piece: " + colour + " at (" + this.getX() + ", " + this.getY() + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Piece) {
            Piece p = (Piece) o;
            return (this.colour == p.getColour() && this.position.equals(p.getPosition()));
        }
        return false;
    }
}