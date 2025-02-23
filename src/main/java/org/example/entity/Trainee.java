package org.example.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Trainee extends User {
    private String dateOfBirth;
    private String address;

    public Trainee(Long userId, String firstName, String lastName, String username, String password, String dateOfBirth, String address) {
        super(userId, firstName, lastName, username, password, true);
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
}
