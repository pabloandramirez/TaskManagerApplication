package com.gamedev.taskManager.repository.developer;

import com.gamedev.taskManager.domain.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeveloperRepository extends JpaRepository<Developer, UUID> {
}
