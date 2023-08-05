package com.gamedev.taskManager.domain;

import com.gamedev.taskManager.bootstrap.constants.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Developer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID uuid;

    @Column(length = 50, columnDefinition = "varchar(50)", updatable = true, nullable = false)
    private String name;

    @Column(length = 100, columnDefinition = "varchar(100)", updatable = true, nullable = false)
    private String email;

    @Column(length = 20, columnDefinition = "varchar(20)", updatable = true, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    private Game game;

    @OneToMany(mappedBy = "developer")
    private List<Task> taskList = new ArrayList<>();

    public void setGame(Game game) {
        this.game = game;
        game.getDevelopers().add(this);
    }

}
