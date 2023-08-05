package com.gamedev.taskManager.mapper.game.impl;

import com.gamedev.taskManager.domain.Game;
import com.gamedev.taskManager.mapper.game.GameMapper;
import com.gamedev.taskManager.model.GameDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class GameMapperImpl implements GameMapper {

    @Override
    public Game gameDTOtoGame(GameDTO gameDTO) {
        return Game.builder()
                .uuid(UUID.randomUUID())
                .title(gameDTO.getTitle())
                .description(gameDTO.getDescription())
                .releaseData(getLocalDateTime(gameDTO.getReleaseData()))
                .build();
    }

    @Override
    public GameDTO gameToGameDTO(Game game) {
        return GameDTO.builder()
                .title(game.getTitle())
                .description(game.getDescription())
                .releaseData(getLocalDateTime(game.getReleaseData()))
                .build();
    }

    private LocalDateTime getLocalDateTime(String date){
        if (!date.isBlank()){
            String[] parts = date.split("/");
            return LocalDateTime.of(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]),0,0);
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
