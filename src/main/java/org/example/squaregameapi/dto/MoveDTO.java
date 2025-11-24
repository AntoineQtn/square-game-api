package org.example.squaregameapi.dto;

/**
 * Represents a Data Transfer Object (DTO) for a move in a game.
 * This class is used to encapsulate the details of a move, including its sequential order
 * and its coordinates on the game board.
 *
 * It is typically used in scenarios where move data needs to be transferred across
 * different application layers, such as between services or between a DAO and an API.
 *
 * Features:
 * - moveOrder: Represents the order or sequence of the move in the game's move history.
 * - x: Specifies the x-coordinate of the move on the game board.
 * - y: Specifies the y-coordinate of the move on the game board.
 */
public class MoveDTO {
    private int moveOrder;
    private int x;
    private int y;

    /**
     * Default constructor for the MoveDTO class.
     * Initializes an instance of the MoveDTO without setting any properties.
     * Typically used when an object needs to be created and its properties
     * are meant to be set later.
     */
    public MoveDTO() {}

    /**
     * Constructs a new MoveDTO instance with the specified move order and coordinates.
     *
     * @param moveOrder The sequential order of the move in the game's move history.
     * @param x The x-coordinate of the move on the game board.
     * @param y The y-coordinate of the move on the game board.
     */
    public MoveDTO(int moveOrder, int x, int y) {
        this.moveOrder = moveOrder;
        this.x = x;
        this.y = y;
    }

    // Getters et Setters
    public int getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(int moveOrder) {
        this.moveOrder = moveOrder;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}