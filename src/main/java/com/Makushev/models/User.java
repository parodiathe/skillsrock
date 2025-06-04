package com.Makushev.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    private UUID UUID;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "The Full Name can't be empty.")
    @JsonProperty("FIO")
    private String FIO;

    @Column(nullable = false, length = 20, unique = true)
    @NotNull(message = "The phone number cannot be null")
    private Long phoneNumber;

    @Column(length = 200)
    @NotBlank(message = "The avatar can't be empty.")
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "Role", nullable = false)
    private Role role;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String roleName;

    public User() {
        this.UUID = UUID.randomUUID();
    }

}
