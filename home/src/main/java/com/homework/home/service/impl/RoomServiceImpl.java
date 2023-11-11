package com.homework.home.service.impl;

import com.homework.home.dto.request.RoomRequest;
import com.homework.home.entity.HomeEntity;
import com.homework.home.entity.RoomEntity;
import com.homework.home.repository.HomeEntityRepository;
import com.homework.home.repository.RoomEntityRepository;
import com.homework.home.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final RoomEntityRepository roomEntityRepository;
    private final HomeEntityRepository homeEntityRepository;

    public RoomServiceImpl(HomeEntityRepository homeEntityRepository, RoomEntityRepository roomEntityRepository, HomeEntityRepository homeEntityRepository1) {
        this.roomEntityRepository = roomEntityRepository;
        this.homeEntityRepository = homeEntityRepository1;
    }

    @Override
    public RoomEntity createRoom(Long homeId, RoomRequest roomRequest) {
        HomeEntity homeEntity = homeEntityRepository.findById(homeId).orElse(null);
        if (homeEntity != null) {
            RoomEntity roomEntity = RoomEntity.builder()
                    .name(roomRequest.getName())
                    .home(homeEntity).build();
            roomEntityRepository.save(roomEntity);
            return roomEntity;
        }
        return null;
    }

    @Override
    public Boolean deleteRoom(Long id) {
        if (roomEntityRepository.existsById(id)) {
            roomEntityRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public RoomEntity getRoom(Long id) {
        RoomEntity roomEntity = roomEntityRepository.findById(id).orElse(null);
        return roomEntity;
    }

    @Override
    public Boolean changeRoom(Long id, RoomRequest roomRequest) {
        Optional<RoomEntity> roomEntity = roomEntityRepository.findById(id);
        if (roomEntity.isPresent()) {
            RoomEntity tempEntity = RoomEntity.builder()
                    .id(id)
                    .name(roomRequest.getName())
                    .home(roomEntity.get().getHome())
                    .build();
            roomEntityRepository.save(tempEntity);
            return true;
        }
        return false;
    }
}
