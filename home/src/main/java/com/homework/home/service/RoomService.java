package com.homework.home.service;

import com.homework.home.dto.request.HomeRequest;
import com.homework.home.dto.request.RoomRequest;
import com.homework.home.entity.HomeEntity;
import com.homework.home.entity.RoomEntity;

import java.util.List;

public interface RoomService {
    Boolean deleteRoom(Long id);
    Boolean changeRoom(Long id, RoomRequest roomRequest);

    RoomEntity createRoom(Long homeId, RoomRequest roomRequest);

    RoomEntity getRoom(Long id);
}
