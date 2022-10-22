package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LombokReqresRequest {
    private String email;
    private String password;
    @JsonProperty("first_name")
    private String name;
    @JsonProperty("last_name")
    private String lastName;
    private String job;
}
