package com.homework.home.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class RoomRequest {
    @NotBlank
    @Length(min = 1, max = 100)
    private String name;
}

