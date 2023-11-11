package com.homework.home.repository;

import com.homework.home.entity.HomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HomeEntityRepository extends JpaRepository<HomeEntity,Long> {
    @Query("SELECT h FROM HomeEntity h ORDER BY h.id ASC")
    List<HomeEntity> findAllHomesOrderedByIdASC();
}
