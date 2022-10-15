package models;

import lombok.Data;

@Data
public class LombokReqresRequest {
    private String email,
            password,
            name,
            job;
}
