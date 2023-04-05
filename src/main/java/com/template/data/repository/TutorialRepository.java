package com.template.data.repository;

import java.util.List;

import com.template.data.entity.TutorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<TutorialEntity, Long> {
    List<TutorialEntity> findByPublished(boolean published);

    List<TutorialEntity> findByTitleContaining(String title);
}