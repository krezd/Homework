package com.homework.home.dto;

import com.homework.home.dto.request.HomeRequest;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Home {

    @JsonView(Views.Public.class)
    private Integer id;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Private.class)
    private String address;

    public void create(HomeRequest homeRequest, Integer id) {
        this.id = id;
        this.name = homeRequest.getName();
        this.address = homeRequest.getAddress();

    }
}
