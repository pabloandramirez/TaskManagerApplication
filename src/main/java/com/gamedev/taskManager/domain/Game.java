package com.gamedev.taskManager.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Game {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @Column(length = 100, columnDefinition = "varchar(100)", updatable = true, nullable = false)
    private String title;

    @Column(length = 500, columnDefinition = "varchar(100)", updatable = true, nullable = false)
    private String description;

    @OneToMany(mappedBy = "game")
    private List<Developer> developers = new ArrayList<>();

    @OneToMany(mappedBy = "game")
    private List<Task> tasks = new ArrayList<>();

    @Column(columnDefinition = "DATE", updatable = true, nullable = false)
    private LocalDateTime releaseData;

}
