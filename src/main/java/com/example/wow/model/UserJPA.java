package com.example.wow.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user_jpa")
public class UserJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "first_name")
    private String name;
    private String email;

    @Transient
    private Integer age; // Not a column in the database

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    public void onPrePersist() {
        createdAt = LocalDateTime.now();
    }

    @PostPersist
    public void onPostPersist() {
        System.out.println("User with ID " + id + " has been persisted.");
    }

    private LocalDateTime updatedAt;

    @PreUpdate
    public void onPreUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @PostUpdate
    public void onPostUpdate() {
        System.out.println("User with ID " + id + " has been updated.");
    }

    private Boolean active;

    @PreRemove
    public void onPreRemove() {
        this.active = false;
    }

    @PostRemove
    public void onPostRemove() {
        System.out.println("User with ID " + id + " has been removed.");
    }

    private String firstName;
    private String lastName;

    @Transient
    private String fullName; // Not a column in the database

    @PostLoad
    public void onPostLoad() {
        this.fullName = firstName + lastName;
    }

}
