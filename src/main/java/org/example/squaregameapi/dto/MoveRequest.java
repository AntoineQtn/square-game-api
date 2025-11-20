package org.example.squaregameapi.dto;

/**
 * Represents a request to perform a move in a game.
 * This class is used to encapsulate the coordinates of a move
 * intended to be made in a game session. It is typically used
 * as request body data in an API that supports game actions.
 *
 * The move is defined by the following properties:
 * - x: The horizontal coordinate of the move.
 * - y: The vertical coordinate of the move.
 *
 * Instances of this class can be utilized in methods or endpoints
 * that handle game move logic.
 */
public class MoveRequest {
    private int x;
    private int y;

    public MoveRequest() {
    }

    public MoveRequest(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
}
