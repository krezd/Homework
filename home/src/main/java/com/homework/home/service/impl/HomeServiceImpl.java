package com.homework.home.service.impl;

import com.homework.home.dto.request.HomeRequest;
import com.homework.home.dto.request.RoomRequest;
import com.homework.home.entity.HomeEntity;
import com.homework.home.entity.RoomEntity;
import com.homework.home.repository.HomeEntityRepository;
import com.homework.home.repository.RoomEntityRepository;
import com.homework.home.service.HomeService;
import org.hibernate.annotations.OrderBy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HomeServiceImpl implements HomeService {
    private final HomeEntityRepository homeEntityRepository;
    private final RoomEntityRepository roomEntityRepository;

    public HomeServiceImpl(HomeEntityRepository homeEntityRepository, RoomEntityRepository roomEntityRepository) {
        this.homeEntityRepository = homeEntityRepository;
        this.roomEntityRepository = roomEntityRepository;
    }

    @Override
    public HomeEntity createHome(HomeRequest homeRequest) {
        HomeEntity homeEntity = HomeEntity.builder()
                .name(homeRequest.getName())
                .address(homeRequest.getAddress())
                .build();
        homeEntityRepository.save(homeEntity);
        return homeEntity;
    }

    @Override
    public List<HomeEntity> getHomes() {
        return homeEntityRepository.findAllHomesOrderedByIdASC();
    }

    @Override
    public HomeEntity getHome(Long id) {
        Optional<HomeEntity> homeEntity = homeEntityRepository.findById(id);
        if (homeEntity.isPresent()) {
            return homeEntity.get();
        }
        return null;
    }

    @Override
    public Boolean changeHome(Long id, HomeRequest homeRequest) {
        Optional<HomeEntity> homeEntity = homeEntityRepository.findById(id);
        if (homeEntity.isPresent()) {
            HomeEntity tempEntity = HomeEntity.builder()
                    .id(id)
                    .name(homeRequest.getName())
                    .address(homeRequest.getAddress())
                    .build();
            homeEntityRepository.save(tempEntity);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteHome(Long id) {
        if (homeEntityRepository.existsById(id)) {
            homeEntityRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
