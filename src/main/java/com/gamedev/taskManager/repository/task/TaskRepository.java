package com.gamedev.taskManager.repository.task;

import com.gamedev.taskManager.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
