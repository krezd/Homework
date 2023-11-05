package com.homework.home.service;

import com.homework.home.dto.Home;
import com.homework.home.dto.request.HomeRequest;

import java.util.List;

public interface HomeService {
    Home createHome(HomeRequest homeRequest);
    List<Home> getHomes();
    Home getHome(Integer id);
    Boolean changeHome(Integer id, HomeRequest homeRequest);
    Boolean deleteHome(Integer id);
}
