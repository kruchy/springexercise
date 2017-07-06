package pl.edu.agh.kruchy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class User implements Serializable {

    @NonNull
    Username username;

    @NonNull
    Password password;
}
