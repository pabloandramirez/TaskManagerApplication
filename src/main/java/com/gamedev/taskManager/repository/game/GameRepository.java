package com.gamedev.taskManager.repository.game;

import com.gamedev.taskManager.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {
}
