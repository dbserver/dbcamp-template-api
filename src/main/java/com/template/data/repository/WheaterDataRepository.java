package com.template.data.repository;

import com.template.data.entity.WheaterDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WheaterDataRepository extends JpaRepository <WheaterDataEntity, Long> {

    List<WheaterDataEntity> findAllByOrderByDateDesc();


}
