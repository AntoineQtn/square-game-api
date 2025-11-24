package org.example.squaregameapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    private String id;
    @Column(name = "game_type", nullable = false, length = 50)
    private String gameType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GameEntity() {

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

}
