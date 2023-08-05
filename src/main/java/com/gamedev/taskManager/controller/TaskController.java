package com.gamedev.taskManager.controller;

import com.gamedev.taskManager.bootstrap.constants.State;
import com.gamedev.taskManager.domain.Task;
import com.gamedev.taskManager.exceptions.NotFoundException;
import com.gamedev.taskManager.mapper.task.TaskMapper;
import com.gamedev.taskManager.model.TaskDTO;
import com.gamedev.taskManager.repository.task.TaskRepository;
import com.gamedev.taskManager.services.task.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskRepository taskRepository;

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    @GetMapping("/developer/{developerId}")
    public List<TaskDTO> getTasksByDeveloperId(@PathVariable(value = "developerId")UUID developerId)
            throws NotFoundException {
        return taskService.getTasksByDeveloperId(developerId);
    }

    @GetMapping("/game/{gameId}")
    public List<TaskDTO> getTasksByGameId(@PathVariable(value = "gameId")UUID gameId)
            throws NotFoundException {
        return taskService.getTasksByGameId(gameId);
    }

    @PutMapping("/{taskId}/changeState")
    public ResponseEntity<Void> changeState(@PathVariable(name = "taskId") UUID taskId ,
                                      @RequestParam (name = "state", required = false) String state) throws NotFoundException {
        if(!taskService.changeState(taskId, state)){
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/filterBy")
    public List<TaskDTO> getTasksByStateOrDeadline
            (@RequestParam(name = "state", required = false) String state,
             @RequestParam(name = "deadLine", required = false) String deadLine){
        return taskService.getTasksByStateOrDeadline(state, deadLine);
    }

    @GetMapping("/noCompleted")
    public List<TaskDTO> getTasksAfterDeadline(){
        return taskService.getTasksAfterDeadline();
    }
}
