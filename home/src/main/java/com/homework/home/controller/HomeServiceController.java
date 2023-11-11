package com.homework.home.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.homework.home.dto.Home;
import com.homework.home.dto.Views;
import com.homework.home.dto.request.HomeRequest;
import com.homework.home.dto.request.RoomRequest;
import com.homework.home.entity.HomeEntity;
import com.homework.home.entity.RoomEntity;
import com.homework.home.service.HomeService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api",produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeServiceController {
    private final HomeService homeService;

    public HomeServiceController(HomeService homeService){
        this.homeService = homeService;
    }

    @GetMapping("/get")
    public String getString(){
        return "Hello home!";
    }
    @PostMapping("/homes")
    public ResponseEntity<HomeEntity> createHome(@RequestBody @Valid HomeRequest request){
        HomeEntity homeEntity = homeService.createHome(request);
        return ResponseEntity.ok(homeEntity);
    }
    @GetMapping("/homes")
    @JsonView(Views.Public.class)
    public ResponseEntity<List<HomeEntity>> getHomes(){
        return ResponseEntity.ok(homeService.getHomes());
    }

    @GetMapping("/homes/{homeId}")
    public ResponseEntity<HomeEntity> getHomeById(@PathVariable Long homeId){
        HomeEntity homeEntity = homeService.getHome(homeId);
        if(homeEntity != null){
            return ResponseEntity.ok(homeEntity);
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping ("/homes/{homeId}")
    public ResponseEntity<HomeEntity> changeHome(@PathVariable Long homeId,@RequestBody @Valid HomeRequest request){
        if(homeService.changeHome(homeId,request)){
            return ResponseEntity.ok(homeService.getHome(homeId));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping  ("/homes/{homeId}")
    public ResponseEntity<String> deleteHome(@PathVariable Long homeId){
        if(homeService.deleteHome(homeId)){
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.notFound().build();
    }
}
