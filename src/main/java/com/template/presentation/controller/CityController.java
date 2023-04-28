package com.template.presentation.controller;

import com.template.business.services.CityService;
import com.template.data.entity.CityEntity;
import com.template.dto.CityRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4767")
@RestController
@RequestMapping("/api/v1/weather")
public class CityController {

    final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/register-city")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> post(@RequestBody CityRequestDTO cityRequestDTO) throws IOException {
        CityEntity cityEntity = new CityEntity();

        BeanUtils.copyProperties(cityRequestDTO, cityEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.save(cityEntity));
    }
    @GetMapping("/cities/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CityEntity>> getAll() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(cityService.findAll());
    }

}
