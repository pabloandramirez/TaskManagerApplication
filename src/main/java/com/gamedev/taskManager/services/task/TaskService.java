package com.gamedev.taskManager.services.task;

import com.gamedev.taskManager.exceptions.NotFoundException;
import com.gamedev.taskManager.model.TaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskService {
    List<TaskDTO> getTasksByDeveloperId(UUID developerId) throws NotFoundException;

    Optional<TaskDTO> getTaskById(UUID taskId);

    List<TaskDTO> getTasksByStateOrDeadline(String state, String deadLine);

    boolean changeState(UUID taskId , String state);

    List<TaskDTO> getTasksByGameId(UUID gameId) throws NotFoundException;

    List<TaskDTO> getTasksAfterDeadline();
}
