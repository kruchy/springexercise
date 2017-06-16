package pl.edu.agh.kruchy.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class User implements Serializable {

    @NonNull
    String username;

    @NonNull
    String password;
}