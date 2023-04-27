package com.template.data.repository;

import com.template.data.entity.WheaterDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WheaterDataRepository extends JpaRepository <WheaterDataEntity, Long> {
}
