package model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class User {
    private String email;
    private String password;
    private boolean status;
}