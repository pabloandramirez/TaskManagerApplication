package com.gamedev.taskManager.mapper.task;

import com.gamedev.taskManager.domain.Task;
import com.gamedev.taskManager.model.TaskDTO;

public interface TaskMapper {

    Task taskDTOtoTask(TaskDTO taskDTO);

    TaskDTO taskToTaskDTO(Task task);
}
