package com.gamedev.taskManager.domain;

import com.gamedev.taskManager.bootstrap.constants.State;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @Column(length = 500, columnDefinition = "varchar(500)", updatable = true, nullable = false)
    private String description;

    @ManyToOne
    private Game game;

    @ManyToOne
    private Developer developer;

    @Column(columnDefinition = "DATE", updatable = true, nullable = false)
    private LocalDateTime deadline;

    @Column(length = 500, columnDefinition = "varchar(500)", updatable = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private State state;

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}
