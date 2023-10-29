import java.util.Set;
import java.util.HashSet;

public class GameState {
    private HashSet<Piece> pieces;
    private int player;

    public GameState() {
        pieces = new HashSet<Piece>();
        reset();
    }

    public void reset() {
        int width = 8;
        int height = 8;
        // set piece positions
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < width; j++) {
                if (i % 2 == 0) {
                    if (j % 2 == 1) {
                        pieces.add(new Piece(1, new Position(i, j)));
                        pieces.add(new Piece(-1, new Position(height - i - 1, width - j - 1)));
                    }
                } 
                else {
                    if (j % 2 == 0) {
                        pieces.add(new Piece(1, new Position(i, j)));
                        pieces.add(new Piece(-1, new Position(height - i - 1, width - j - 1)));
                    }
                }
            }
        }
    }

    
    public Piece getPiece(int i, int j) {
        for (Piece piece : pieces) {
            if (piece.getX() == i && piece.getY() == j) {
                return piece;
            }
        }
        return null;
    }

    public Set<Move> getValidMoves() {
        Set<Move> validMoves = new HashSet<Move>();
        for (Piece piece : pieces) {
            if (piece.getColour() == player) {
                validMoves.addAll(getValidMoves(piece));
            }
        }
        return validMoves;
    }

    public Set<Move> getValidMoves(Piece piece) {
        Set<Move> validMoves = new HashSet<Move>();

        int x = piece.getX();
        int y = piece.getY();
        int colour = piece.getColour();

        // check if piece is a king
        boolean isKing = piece.isKing();

        // check for valid moves in all directions
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        for (int[] dir : directions) {
            int dx = dir[0];
            int dy = dir[1];

            // check for valid moves in the forward direction
            if (colour == 1 || isKing) {
                int newX = x + dx;
                int newY = y + dy;
                if (!outOfBounds(newX, newY)) {
                    Piece targetPiece = getPiece(newX, newY);
                    if (targetPiece == null) {
                        validMoves.add(new Move(piece, new Position(newX, newY)));
                    } 
                    else if (targetPiece.getColour() != colour) { // check for hop over opponent
                        newX += dx;
                        newY += dy;
                        if (!outOfBounds(newX, newY)) {
                            targetPiece = getPiece(newX, newY);
                            if (targetPiece == null) {
                                validMoves.add(new Move(piece, new Position(newX, newY)));
                            }
                        }
                    }
                }
            }

            // check for valid moves in the backward direction
            if (colour == -1 || isKing) {
                int newX = x - dx;
                int newY = y - dy;
                if (!outOfBounds(newX, newY)) {
                    Piece targetPiece = getPiece(newX, newY);
                    if (targetPiece == null) {
                        validMoves.add(new Move(piece, new Position(newX, newY)));
                    } 
                    else if (targetPiece.getColour() != colour) { // check for hop over opponent
                        newX -= dx;
                        newY -= dy;
                        if (!outOfBounds(newX, newY)) {
                            targetPiece = getPiece(newX, newY);
                            if (targetPiece == null) {
                                validMoves.add(new Move(piece, new Position(newX, newY)));
                            }
                        }
                    }
                }
            }
        }

        return validMoves;
    }

    private boolean outOfBounds(int x, int y) {
        return (x < 0 || x > 7 || y < 0 || y > 7);
    }

    public boolean gameOver() {
        for (Piece piece : pieces) {
            if (piece.getColour() == player) {
                if (getValidMoves(piece).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        int[][] board = new int[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] =0;
            }
        }
        for (Piece piece : pieces) {
            board[piece.getX()][piece.getY()] = piece.getColour();
        }
        String str = "";
        for (int[] row : board) {
            str += "|\t";
            for (int piece : row) {
                str += piece + "\t|\t";
            }
            str += "\n";
        }
        return str;
    }
}