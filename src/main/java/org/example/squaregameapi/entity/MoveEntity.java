package org.example.squaregameapi.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

/**
 * Entity representing a move in a game. Each move stores the game it belongs to, the order
 * of the move, the coordinates of the move, and the timestamp when it was created.
 *
 * This entity is mapped to the "moves" table in the database.
 *
 * Fields:
 * - id: Unique identifier for the move.
 * - game: Reference to the associated game entity.
 * - moveOrder: Order of the move within the game.
 * - x: X-coordinate of the move on the game board.
 * - y: Y-coordinate of the move on the game board.
 * - createdAt: Timestamp when the move was created, automatically populated.
 */
@Entity
@Table(name = "moves")
public class MoveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    @Column(name = "move_order", nullable = false)
    private Integer moveOrder;

    @Column(nullable = false)
    private Integer x;

    @Column(nullable = false)
    private Integer y;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Default constructor for the MoveEntity class.
     *
     * This constructor creates an instance of the MoveEntity class
     * without initializing any of its fields. It is required by JPA
     * for entity instantiation during database operations.
     *
     *
     * Use this constructor when creating a MoveEntity instance
     * through the JPA repository or when the field values will be set
     * manually after instantiation.
     */
    public MoveEntity() {
    }

    /**
     * Constructs a new MoveEntity instance with the specified game, move order, and coordinates.
     *
     * @param game the GameEntity to which the move belongs
     * @param moveOrder the order of the move within the game
     * @param x the x-coordinate of the move on the game board
     * @param y the y-coordinate of the move on the game board
     */
    public MoveEntity(GameEntity game, Integer moveOrder, Integer x, Integer y) {
        this.game = game;
        this.moveOrder = moveOrder;
        this.x = x;
        this.y = y;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameEntity getGame() {
        return game;
    }

    public void setGame(GameEntity game) {
        this.game = game;
    }

    public Integer getMoveOrder() {
        return moveOrder;
    }

    public void setMoveOrder(Integer moveOrder) {
        this.moveOrder = moveOrder;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Callback method automatically invoked by JPA before persisting a new entity.
     *
     * This method sets the createdAt timestamp to the current date and time
     * when an entity is first persisted to the database. It ensures that the
     * createdAt field is populated correctly for newly created entities.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
