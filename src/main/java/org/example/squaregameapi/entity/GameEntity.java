package org.example.squaregameapi.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * Entity class representing a game in the application.
 *
 * This entity is mapped to the "games" table in the database.
 * It stores information about a game, such as its unique identifier,
 * type, and timestamps indicating when the game was created and last updated.
 *
 * Fields:
 * - id: Unique identifier for the game.
 * - gameType: Describes the type of the game, with a maximum length of 50 characters.
 * - createdAt: Timestamp indicating when the game was created. Automatically populated.
 * - updatedAt: Timestamp indicating when the game was last updated. Automatically populated.
 *
 * The class includes default constructors, as well as constructors and setter methods
 * to initialize and modify the fields, and getter methods to access their values.
 */
@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    private String id;

    @Column(name = "game_type", nullable = false, length = 50)
    private String gameType;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //JPA requires an empty constructor
    public GameEntity() {
    }

    public GameEntity(String id, String gameType) {
        this.id = id;
        this.gameType = gameType;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public String getGameType() {
        return gameType;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Lifecycle callback method annotated with {@code @PrePersist}, which is
     * invoked automatically before a new entity is persisted in the database.
     *
     * The purpose of this method is to initialize the {@code createdAt} and
     * {@code updatedAt} fields with the current date and time. This ensures that
     * these fields are populated with the correct timestamps when the entity
     * is first saved.
     *
     * This method is automatically executed by the JPA provider and does not
     * require explicit invocation.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Lifecycle callback method annotated with {@code @PreUpdate}, which is
     * invoked automatically before an existing entity is updated in the database.
     *
     * The purpose of this method is to update the {@code updatedAt} field
     * with the current date and time, ensuring that the timestamp reflects
     * the most recent modification of the entity.
     *
     * This method is automatically executed by the JPA provider and does not
     * require explicit invocation.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
