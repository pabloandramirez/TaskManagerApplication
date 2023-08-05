package com.gamedev.taskManager.services.developer.impl;

import com.gamedev.taskManager.domain.Developer;
import com.gamedev.taskManager.domain.Game;
import com.gamedev.taskManager.domain.Task;
import com.gamedev.taskManager.exceptions.NotFoundException;
import com.gamedev.taskManager.mapper.developer.DeveloperMapper;
import com.gamedev.taskManager.mapper.task.TaskMapper;
import com.gamedev.taskManager.model.DeveloperDTO;
import com.gamedev.taskManager.model.TaskDTO;
import com.gamedev.taskManager.repository.developer.DeveloperRepository;
import com.gamedev.taskManager.repository.game.GameRepository;
import com.gamedev.taskManager.repository.task.TaskRepository;
import com.gamedev.taskManager.services.developer.DeveloperService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DeveloperServiceJPAImpl implements DeveloperService {

    private final DeveloperRepository developerRepository;

    private final DeveloperMapper developerMapper;
    private final GameRepository gameRepository;

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    public Optional<DeveloperDTO> getDeveloperById(UUID idDeveloper) {
        Optional<Developer> developerOptional = developerRepository.findById(idDeveloper);
        if(developerOptional.isPresent()){
            return Optional.of(developerMapper.developerToDeveloperDTO(developerOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public Developer createDeveloper(DeveloperDTO developerDTO) {

        Developer developer = developerMapper.developerDTOtoDeveloper(developerDTO);
        return developerRepository.save(developer);
    }

    @Override
    public boolean assignTask(UUID developerId, UUID gameId, TaskDTO taskDTO) throws NotFoundException {
        if (gameRepository.findById(gameId).isEmpty() || developerRepository.findById(developerId).isEmpty()) {
            throw new NotFoundException("Wrong developer or game ID");
        } else {
            Game game = gameRepository.findById(gameId).orElse(null);
            Developer developer = developerRepository.findById(developerId).orElse(null);
            Task task = taskMapper.taskDTOtoTask(taskDTO);
            task.setUuid(UUID.randomUUID());

            if (game!= null && developer != null) {
                task.setDeveloper(developer);
                task.setGame(game);
                developer.getTaskList().add(task);
                game.getTasks().add(task);

                taskRepository.save(task);

                return true;
            }
        }
        return false;
    }
}
