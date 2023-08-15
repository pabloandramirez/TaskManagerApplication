package com.gamedev.taskManager.services.game;

import com.gamedev.taskManager.domain.Developer;
import com.gamedev.taskManager.domain.Game;
import com.gamedev.taskManager.exceptions.NotFoundException;
import com.gamedev.taskManager.model.DeveloperDTO;
import com.gamedev.taskManager.model.GameDTO;
import com.gamedev.taskManager.model.TaskDTO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameService {

    Game createGame(@RequestBody GameDTO gameDTO);

    boolean assignDeveloper(UUID gameId, UUID developerId) throws NotFoundException;

    List<GameDTO> allGames();

    List<GameDTO> allGamesInDevelopment();

    List<GameDTO> allFinishedGames();

    List<GameDTO> gamesByState(String state);

    List<DeveloperDTO> allGameDevelopers(Game game);

    Optional<GameDTO> getGameById(UUID gameId);

    boolean assignTask(UUID gameId, UUID developerId, TaskDTO taskDTO) throws NotFoundException;
}
