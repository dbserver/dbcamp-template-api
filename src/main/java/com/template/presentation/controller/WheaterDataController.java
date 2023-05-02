package com.template.presentation.controller;

import com.template.business.services.WheaterDataService;
import com.template.data.entity.WheaterDataEntity;
import com.template.dto.WheaterDataRequestDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4767")
@RestController
@RequestMapping("/api/v1/weather")
public class WheaterDataController {

    final WheaterDataService wheaterDataService;

    public WheaterDataController(WheaterDataService wheaterDataService) {
        this.wheaterDataService = wheaterDataService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WheaterDataRequestDTO> post(@RequestBody WheaterDataRequestDTO wheaterDataRequestDTO) throws IOException {
        WheaterDataEntity wheaterDataEntity = new WheaterDataEntity();

        BeanUtils.copyProperties(wheaterDataRequestDTO, wheaterDataEntity);
        WheaterDataEntity save = wheaterDataService.save(wheaterDataEntity);
        wheaterDataRequestDTO.setIdWheaterData(save.getIdWheaterData());

        return ResponseEntity.status(HttpStatus.CREATED).body(wheaterDataRequestDTO);
    }

    @GetMapping("/list-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WheaterDataEntity>> getAll() throws IOException {
        var allWeatherData = wheaterDataService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(allWeatherData);
    }

    @GetMapping("/{cityName}/list-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WheaterDataEntity>> getAllBy(@PathVariable String cityName) throws IOException {
        var allWeatherData = wheaterDataService.findAllByName(cityName, Sort.by("date").descending());

        return ResponseEntity.status(HttpStatus.OK).body(allWeatherData);
    }

    @GetMapping("/{cityName}/list-all-day")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WheaterDataEntity>> getBy(@PathVariable String cityName) throws IOException {
        LocalDate today = LocalDate.now();
        List<WheaterDataEntity> todayWeatherData = wheaterDataService.findDateCurrent(today, cityName);

        return ResponseEntity.status(HttpStatus.OK).body(todayWeatherData);
    }

    @GetMapping("/{cityName}/list-all-week")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WheaterDataEntity>> getAll(@PathVariable String cityName) throws IOException {
        var wheaterData = wheaterDataService.findAllNextDays(cityName, Sort.by("date").ascending());
        return ResponseEntity.ok(wheaterData);
    }

    @GetMapping("/list-all-page")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<WheaterDataEntity>> get(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) throws IOException {
        Pageable paging = PageRequest.of(page, size, Sort.by("date").descending());
        Page<WheaterDataEntity> pageResult = wheaterDataService.findAllPage(paging);

        if (pageResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @PutMapping("/{idWheaterData}")
    public ResponseEntity<WheaterDataEntity> put(@PathVariable(value = "idWheaterData") Long idWheaterData, @RequestBody WheaterDataRequestDTO wheaterDataRequestDTO) throws IOException{
        WheaterDataEntity updateWheaterDataEntity = wheaterDataService.update(idWheaterData, wheaterDataRequestDTO);
        return ResponseEntity.ok(updateWheaterDataEntity);
    }

    @DeleteMapping("/{idWheaterData}")
    public ResponseEntity<Void> delete(@PathVariable(value = "idWheaterData") Long idWheaterData) throws IOException{
        wheaterDataService.deleteById(idWheaterData);
        return ResponseEntity.noContent().build();
    }
}
