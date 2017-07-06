package pl.edu.agh.kruchy.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;


@Data
@AllArgsConstructor(suppressConstructorProperties = true)
@NoArgsConstructor
public class Password implements Serializable {
    @NonNull
    @JsonProperty("password")

    String password;
}
