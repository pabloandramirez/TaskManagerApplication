package com.gamedev.taskManager.services.task.impl;

import com.gamedev.taskManager.bootstrap.constants.State;
import com.gamedev.taskManager.domain.Developer;
import com.gamedev.taskManager.domain.Game;
import com.gamedev.taskManager.domain.Task;
import com.gamedev.taskManager.exceptions.NotFoundException;
import com.gamedev.taskManager.mapper.task.TaskMapper;
import com.gamedev.taskManager.model.TaskDTO;
import com.gamedev.taskManager.repository.developer.DeveloperRepository;
import com.gamedev.taskManager.repository.game.GameRepository;
import com.gamedev.taskManager.repository.task.TaskRepository;
import com.gamedev.taskManager.services.task.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class TaskServiceJPAImpl implements TaskService {

    private final DeveloperRepository developerRepository;

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final GameRepository gameRepository;

    @Override
    public List<TaskDTO> getTasksByDeveloperId(UUID developerId) throws NotFoundException {
        Optional<Developer> developerOptional = developerRepository.findById(developerId);
        List<TaskDTO> taskDTOList = new ArrayList<>();
        if (developerOptional.isEmpty()){
            throw new NotFoundException();
        } else {
            for (Task task: developerOptional.get().getTaskList()) {
                taskDTOList.add(taskMapper.taskToTaskDTO(task));
            }
        }
        return taskDTOList;
    }

    @Override
    public Optional<TaskDTO> getTaskById(UUID taskId) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if(taskOptional.isPresent()){
            return Optional.of(taskMapper.taskToTaskDTO(taskOptional.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<TaskDTO> getTasksByStateOrDeadline(String state, String deadLine) {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        if(state != null && !state.isBlank()){
            for (Task task: taskRepository.findAll()) {
                if (task.getState().getState().equalsIgnoreCase(state)){
                    taskDTOList.add(taskMapper.taskToTaskDTO(task));
                }
            }
        } else if (deadLine != null && !deadLine.isBlank()){
            for (Task task: taskRepository.findAll()) {
                if (task.getDeadline().isEqual(Objects.requireNonNull(getLocalDateTime(deadLine)))){
                    taskDTOList.add(taskMapper.taskToTaskDTO(task));
                }
            }
        }
        return taskDTOList;
    }

    @Override
    public boolean changeState(UUID taskId, String state) {
        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isPresent()){
            if (state.equalsIgnoreCase("pending")){
                taskOptional.get().setState(State.IN_PROGRESS);
                taskRepository.saveAndFlush(taskOptional.get());
                return true;
            }
            if (state.equalsIgnoreCase("inProgress")){
                taskOptional.get().setState(State.IN_PROGRESS);
                taskRepository.saveAndFlush(taskOptional.get());
                return true;
            }
            if(state.equalsIgnoreCase("completed")){
                taskOptional.get().setState(State.COMPLETED);
                taskRepository.saveAndFlush(taskOptional.get());
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public List<TaskDTO> getTasksByGameId(UUID gameId) throws NotFoundException {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        List<TaskDTO> taskDTOList = new ArrayList<>();
        if (gameOptional.isEmpty()){
            throw new NotFoundException();
        } else {
            for (Task task: gameOptional.get().getTasks()) {
                taskDTOList.add(taskMapper.taskToTaskDTO(task));
            }
        }
        return taskDTOList;
    }

    @Override
    public List<TaskDTO> getTasksAfterDeadline() {
        List<TaskDTO> taskDTOList = new ArrayList<>();
        for (Task task: taskRepository.findAll()) {
            if (task.getDeadline().isBefore(LocalDateTime.now())&&
                    !Objects.equals(task.getState().getState(), State.COMPLETED.getState())){
                taskDTOList.add(taskMapper.taskToTaskDTO(task));
            }
        }
        return taskDTOList;
    }

    private LocalDateTime getLocalDateTime(String date){
        if (!date.isBlank()){
            String[] parts = date.split("/");
            return LocalDateTime.of(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),0,0);
        }
        return null;
    }
}
