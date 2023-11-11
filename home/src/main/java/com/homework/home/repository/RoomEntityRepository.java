package com.homework.home.repository;

import com.homework.home.entity.HomeEntity;
import com.homework.home.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomEntityRepository extends JpaRepository<RoomEntity,Long> {
}
