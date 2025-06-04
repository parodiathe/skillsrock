package com.Makushev.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name = "Roles")
public class  Role {

    @Id
    private UUID UUID;

    @Column(nullable = false, length = 50)
    private String roleName;

    public Role() {
        this.UUID = UUID.randomUUID();
    }

}
