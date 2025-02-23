package org.example.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Trainer extends User {
    private String specialization;

    public Trainer(Long userId, String firstName, String lastName, String username, String password, String specialization) {
        super(userId, firstName, lastName, username, password, true);
        this.specialization = specialization;
    }
}