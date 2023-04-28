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
import java.util.List;

@CrossOrigin(origins = "http://localhost:4767")
@RestController
@RequestMapping("/api/v1/weather")
public class WheaterDataController {

    final WheaterDataService wheaterDataService;

    public WheaterDataController(WheaterDataService wheaterDataService) {
        this.wheaterDataService = wheaterDataService;
    }

    @GetMapping("/list-all-seven")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WheaterDataEntity>> getAllWheaterData() throws IOException {
        var latestWeatherData = wheaterDataService.findAllSeven();
        return ResponseEntity.status(HttpStatus.OK).body(latestWeatherData);
    }

    @GetMapping("/list-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<WheaterDataEntity>> getWheaterData(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("date").descending());
        Page<WheaterDataEntity> pageResult = wheaterDataService.findAllPage(paging);

        if (pageResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> postWheatherData(@RequestBody WheaterDataRequestDTO wheaterDataRequestDTO) throws IOException {
        WheaterDataEntity wheaterDataEntity = new WheaterDataEntity();

        BeanUtils.copyProperties(wheaterDataRequestDTO, wheaterDataEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(wheaterDataService.save(wheaterDataEntity));
    }

}
