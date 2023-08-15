package com.gamedev.taskManager.controller;

import com.gamedev.taskManager.domain.Developer;
import com.gamedev.taskManager.domain.Game;
import com.gamedev.taskManager.exceptions.BadRequestException;
import com.gamedev.taskManager.exceptions.NotFoundException;
import com.gamedev.taskManager.model.DeveloperDTO;
import com.gamedev.taskManager.model.GameDTO;
import com.gamedev.taskManager.model.TaskDTO;
import com.gamedev.taskManager.repository.developer.DeveloperRepository;
import com.gamedev.taskManager.repository.game.GameRepository;
import com.gamedev.taskManager.services.game.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/game")
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameRepository gameRepository;

    private final GameService gameService;

    private final DeveloperRepository developerRepository;

    @PostMapping()
    public ResponseEntity<Void> createGame(@RequestBody GameDTO gameDTO){
        log.info("Creacion de un nuevo juego");
        Game gameCreated = gameService.createGame(gameDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/game/"+gameCreated.getUuid());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{gameId}/assignDeveloper/{developerId}")
    public ResponseEntity<Void> assignDeveloper(@PathVariable(value = "gameId") UUID gameId,
                                                @PathVariable(value = "developerId") UUID developerId)
            throws BadRequestException, NotFoundException {
        if(gameService.assignDeveloper(gameId, developerId)){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/v1/developer/"
                    +developerRepository.findById(developerId).get().getUuid());
            return new ResponseEntity<>(headers, HttpStatus.OK);
        } else {
            throw new BadRequestException();
        }
    }

    @GetMapping()
    public List<GameDTO> gamesByState(@RequestParam(name = "state", required = false) String state){
        return gameService.gamesByState(state);
    }

    @GetMapping("/{gameId}/developers")
    public List<DeveloperDTO> allGameDevelopers(@PathVariable(value = "gameId") UUID gameId)
            throws NotFoundException {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        if(gameOptional.isEmpty()){
            throw new NotFoundException();
        } else{
            //es solo por el mensaje, se debe devolver al cliente la lista vacia
            if(gameService.allGameDevelopers(gameOptional.get()).isEmpty()){
                log.info("There are no developers in this game");
            }
            return gameService.allGameDevelopers(gameOptional.get());
        }
    }

    @PutMapping("/{gameId}/{developerId}/assignTask")
    public ResponseEntity<Void> assignTask(@PathVariable(value = "gameId") UUID gameId,
                                           @PathVariable(value = "developerId") UUID developerId,
                                           @RequestBody TaskDTO taskDTO) throws BadRequestException, NotFoundException {
        if(gameService.assignTask(gameId, developerId, taskDTO)){
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", "/api/v1/task/developer"
                    +developerRepository.findById(developerId).get().getUuid());
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } else {
            throw new BadRequestException();
        }
    }

}
