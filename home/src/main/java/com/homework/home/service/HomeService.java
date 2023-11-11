package com.homework.home.service;

import com.homework.home.dto.Home;
import com.homework.home.dto.request.HomeRequest;
import com.homework.home.dto.request.RoomRequest;
import com.homework.home.entity.HomeEntity;
import com.homework.home.entity.RoomEntity;

import java.util.List;
import java.util.Optional;

public interface HomeService {
    HomeEntity createHome(HomeRequest homeRequest);
    List<HomeEntity> getHomes();
    HomeEntity getHome(Long id);
    Boolean changeHome(Long id, HomeRequest homeRequest);
    Boolean deleteHome(Long id);
}
