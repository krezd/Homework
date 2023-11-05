package com.homework.home.service.impl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homework.home.dto.Home;
import com.homework.home.dto.request.HomeRequest;
import com.homework.home.service.HomeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeServiceImpl implements HomeService {
private List<Home> homeList = new ArrayList<>();
private Integer count = 1;
    @Override
    public Home createHome(HomeRequest homeRequest) {
        Home home = new Home();
        home.create(homeRequest,count);
        homeList.add(home);
        count++;
        return home;
    }

    @Override
    public List<Home> getHomes() {
        return homeList;
    }

    @Override
    public Home getHome(Integer id) {
        if(id < homeList.size()){
            return homeList.get(id);
        }
        return null;
    }
    public Boolean changeHome(Integer id,HomeRequest homeRequest){
        if(id < homeList.size()){
            Home home = new Home();
            home.create(homeRequest,id+1);
            homeList.set(id,home);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteHome(Integer id) {
        if(id < homeList.size() && homeList.get(id) != null ){
            homeList.set(id,null);
            return true;
        }
        return false;
    }
}
