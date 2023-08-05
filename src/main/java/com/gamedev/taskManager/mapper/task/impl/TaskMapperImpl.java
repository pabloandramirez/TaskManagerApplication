package com.gamedev.taskManager.mapper.task.impl;

import com.gamedev.taskManager.bootstrap.constants.Role;
import com.gamedev.taskManager.bootstrap.constants.State;
import com.gamedev.taskManager.domain.Task;
import com.gamedev.taskManager.mapper.task.TaskMapper;
import com.gamedev.taskManager.model.TaskDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public Task taskDTOtoTask(TaskDTO taskDTO) {
        return Task.builder()
                .uuid(UUID.randomUUID())
                .description(taskDTO.getDescription())
                .state(State.PENDING)
                .deadline(getLocalDateTime(taskDTO.getDeadLine()))
                .build();
    }

    @Override
    public TaskDTO taskToTaskDTO(Task task) {
        return TaskDTO.builder()
                .description(task.getDescription())
                .state(task.getState().getState())
                .deadLine(getLocalDateTime(task.getDeadline()))
                .idDeveloper(task.getDeveloper().getUuid().toString())
                .idGame(task.getGame().getUuid().toString())
                .build();
    }

    private State getState(String stateString){
        if(!stateString.isBlank()){
            for (State state: State.values()) {
                if (state.getState().equalsIgnoreCase(stateString)) {
                    return state;
                }
            }
        }
        return null;
    }

    private LocalDateTime getLocalDateTime(String date){
        if (!date.isBlank()){
            String[] parts = date.split("/");
            return LocalDateTime.of(Integer.parseInt(parts[0]),Integer.parseInt(parts[1])
                            ,Integer.parseInt(parts[2]),0,0);
        }
        return null;
    }

    private String getLocalDateTime(LocalDateTime localDateTime){
        StringBuffer stringBuffer = new StringBuffer();
        return stringBuffer.append(localDateTime.getYear())
                .append("/")
                .append(localDateTime.getMonthValue())
                .append("/")
                .append(localDateTime.getDayOfYear())
                .toString();
    }

}
