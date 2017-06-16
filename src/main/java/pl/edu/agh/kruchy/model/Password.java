package pl.edu.agh.kruchy.model;

import lombok.Data;
import lombok.NonNull;

import java.io.Serializable;


@Data
public class Password implements Serializable {
    @NonNull
    String password;
}