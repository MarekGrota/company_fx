package model;

import lombok.*;

@Data
@AllArgsConstructor
public class User {
    private String email;
    private String password;
    private boolean status;
}