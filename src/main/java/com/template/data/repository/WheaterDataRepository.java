package com.template.data.repository;

import com.template.data.entity.WheaterDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WheaterDataRepository extends JpaRepository <WheaterDataEntity, Long> {

    List<WheaterDataEntity> findTopByOrderByDateDesc();


}
