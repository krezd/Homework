package com.homework.home.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.homework.home.dto.Views;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "home_table")
public class HomeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.Public.class)
    private long id;
    @JsonView(Views.Public.class)
    private String name;
    @JsonView(Views.Private.class)
    private String address;
    @OneToMany(mappedBy = "home",cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<RoomEntity> rooms = new ArrayList<>();
}
