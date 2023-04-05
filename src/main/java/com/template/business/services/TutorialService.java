package com.template.business.services;

import com.template.data.entity.TutorialEntity;
import com.template.data.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class TutorialService {
    @Autowired
    TutorialRepository tutorialRepository;

    public List<TutorialEntity> getAll(String title) {

        if (title == null)
            return tutorialRepository.findAll();
        else
            return tutorialRepository.findByTitleContaining(title);
    }

    public Optional<TutorialEntity> getById(@PathVariable("id") long id) {
        return tutorialRepository.findById(id);
    }

    public TutorialEntity create(@RequestBody TutorialEntity tutorial) {
        return tutorialRepository.save(new TutorialEntity(tutorial.getTitle(), tutorial.getDescription(), false));
    }

    public Optional<TutorialEntity> update(@PathVariable("id") long id, @RequestBody TutorialEntity tutorial) {
        Optional<TutorialEntity> tutorialData = tutorialRepository.findById(id);

        if (tutorialData.isPresent()) {
            TutorialEntity _tutorial = tutorialData.get();
            _tutorial.setTitle(tutorial.getTitle());
            _tutorial.setDescription(tutorial.getDescription());
            _tutorial.setPublished(tutorial.isPublished());
            return Optional.of(_tutorial);
        } else {
            return Optional.of(tutorial);
        }
    }

    public void deleteTutorial(@PathVariable("id") long id) {
        tutorialRepository.deleteById(id);
    }

    public List<TutorialEntity> findByPublished(Boolean isPublished) {
        return tutorialRepository.findByPublished(true);
    }

    public void deleteAll() {
        tutorialRepository.deleteAll();
    }
}
