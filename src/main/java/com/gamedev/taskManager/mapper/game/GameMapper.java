package com.gamedev.taskManager.mapper.game;

import com.gamedev.taskManager.model.GameDTO;
import com.gamedev.taskManager.domain.Game;

public interface GameMapper {

    Game gameDTOtoGame(GameDTO gameDTO);

    GameDTO gameToGameDTO(Game game);
}
