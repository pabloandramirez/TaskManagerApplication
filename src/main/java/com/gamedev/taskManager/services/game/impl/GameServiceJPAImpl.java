package com.gamedev.taskManager.services.game.impl;

import com.gamedev.taskManager.domain.Developer;
import com.gamedev.taskManager.domain.Game;
import com.gamedev.taskManager.exceptions.NotFoundException;
import com.gamedev.taskManager.mapper.developer.DeveloperMapper;
import com.gamedev.taskManager.mapper.game.GameMapper;
import com.gamedev.taskManager.model.DeveloperDTO;
import com.gamedev.taskManager.model.GameDTO;
import com.gamedev.taskManager.repository.developer.DeveloperRepository;
import com.gamedev.taskManager.repository.game.GameRepository;
import com.gamedev.taskManager.services.game.GameService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class GameServiceJPAImpl implements GameService {

    private final GameRepository gameRepository;

    private final GameMapper gameMapper;

    private final DeveloperMapper developerMapper;

    private final DeveloperRepository developerRepository;

    @Override
    public Game createGame(GameDTO gameDTO) {
        Game game = gameMapper.gameDTOtoGame(gameDTO);
        return gameRepository.save(game);
    }

    @Override
    public boolean assignDeveloper(UUID gameId, UUID developerId) throws NotFoundException {
        if (gameRepository.findById(gameId).isEmpty() || developerRepository.findById(developerId).isEmpty()) {
            throw new NotFoundException("Wrong developer or game ID");
        } else {
            Game game = gameRepository.findById(gameId).orElse(null);
            Developer developer = developerRepository.findById(developerId).orElse(null);

            if (game!= null && developer != null) {
                developer.setGame(game);
                game.getDevelopers().add(developer);

                gameRepository.save(game);

                return true;
            }
        }
        return false;
    }

    @Override
    public List<GameDTO> allGames() {
        List<GameDTO> gameDTOList = new ArrayList<>();
        for (Game game: gameRepository.findAll()) {
            gameDTOList.add(gameMapper.gameToGameDTO(game));
        }
        return gameDTOList;
    }

    @Override
    public List<GameDTO> allGamesInDevelopment() {
        List<GameDTO> gameDTOList = new ArrayList<>();
        for (Game game: gameRepository.findAll()) {
            if(game.getReleaseData().isAfter(LocalDateTime.now())){
                gameDTOList.add(gameMapper.gameToGameDTO(game));
            }
        }
        return gameDTOList;
    }

    @Override
    public List<GameDTO> allFinishedGames() {
        List<GameDTO> gameDTOList = new ArrayList<>();
        for (Game game: gameRepository.findAll()) {
            if(game.getReleaseData().isBefore(LocalDateTime.now())){
                gameDTOList.add(gameMapper.gameToGameDTO(game));
            }
        }
        return gameDTOList;
    }

    @Override
    public List<GameDTO> gamesByState(String state) {
        if(state != null && !state.isBlank()){
            if (state.equalsIgnoreCase("finished")){
                return allFinishedGames();
            }
            if(state.equalsIgnoreCase("developing")) {
                return allGamesInDevelopment();
            }
        }
        return allGames();
    }

    @Override
    public List<DeveloperDTO> allGameDevelopers(Game game) {
        List<DeveloperDTO> developerDTOList = new ArrayList<>();
        for(Developer developer: game.getDevelopers()){
            developerDTOList.add(developerMapper.developerToDeveloperDTO(developer));
        }
        return developerDTOList;
    }

    @Override
    public Optional<GameDTO> getGameById(UUID gameId) {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if (gameOptional.isPresent()){
            return Optional.of(gameMapper.gameToGameDTO(gameOptional.get()));
        }
        return Optional.empty();
    }


}
