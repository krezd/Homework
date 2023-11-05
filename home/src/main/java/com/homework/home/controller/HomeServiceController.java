package com.homework.home.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.homework.home.dto.Home;
import com.homework.home.dto.Views;
import com.homework.home.dto.request.HomeRequest;
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
    public ResponseEntity<Home> createHome(@RequestBody @Valid HomeRequest request){
        Home home = homeService.createHome(request);
        return ResponseEntity.ok(home);
    }
    @GetMapping("/homes")
    @JsonView(Views.Public.class)
    public ResponseEntity<List<Home>> getHomes(){
        return ResponseEntity.ok(homeService.getHomes());
    }

    @GetMapping("/homes/{homeId}")
    public ResponseEntity<Home> getHomeById(@PathVariable Integer homeId){
        Home home = homeService.getHome(homeId-1);
        if(home != null){
            return ResponseEntity.ok(home);
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping ("/homes/{homeId}")
    public ResponseEntity<Home> changeHome(@PathVariable Integer homeId,@RequestBody @Valid HomeRequest request){
        if(homeService.changeHome(homeId-1,request)){
            return ResponseEntity.ok(homeService.getHome(homeId-1));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping  ("/homes/{homeId}")
    public ResponseEntity<String> deleteHome(@PathVariable Integer homeId){
        if(homeService.deleteHome(homeId-1)){
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.notFound().build();
    }



}
