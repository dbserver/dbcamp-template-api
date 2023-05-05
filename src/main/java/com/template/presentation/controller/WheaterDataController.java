package com.template.presentation.controller;

import com.template.business.services.WheaterDataService;
import com.template.data.entity.WheaterDataEntity;
import com.template.dto.WheaterDataRequestDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
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

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<WheaterDataRequestDTO> post(@RequestBody WheaterDataRequestDTO wheaterDataRequestDTO) throws IOException {
        WheaterDataEntity wheaterDataEntity = new WheaterDataEntity();

        BeanUtils.copyProperties(wheaterDataRequestDTO, wheaterDataEntity);
        WheaterDataEntity save = wheaterDataService.save(wheaterDataEntity);
        wheaterDataRequestDTO.setIdWheaterData(save.getIdWheaterData());

        return ResponseEntity.status(HttpStatus.CREATED).body(wheaterDataRequestDTO);
    }

    @ApiResponse(description = "lista registros de todas as cidades quando NÃO pesquisar a cidade.")
    @GetMapping("/list-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WheaterDataEntity>> getAll() throws IOException {
        var allWeatherData = wheaterDataService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(allWeatherData);
    }

    @ApiResponse(description = "lista todos os registros de uma cidade quando PESQUISAR a cidade.")
    @GetMapping("/{cityName}/list-all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WheaterDataEntity>> getAllBy(@PathVariable String cityName) throws IOException {
        var allWeatherData = wheaterDataService.findAllByName(cityName, Sort.by("date").descending());

        return ResponseEntity.status(HttpStatus.OK).body(allWeatherData);
    }

    @ApiResponse(description = "lista o registro do dia atual mais 6 dias consecutivos.")
    @GetMapping("/{cityName}/list-all-week")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WheaterDataEntity>> getBy(@PathVariable String cityName) throws IOException {
        List<WheaterDataEntity> wheaterDataList = wheaterDataService.findByDateBetween(cityName);

        return new ResponseEntity<>(wheaterDataList, HttpStatus.OK);
    }

    @ApiResponse(description = "lista 10 registros por página quando PESQUISAR uma cidade.")
    @GetMapping("/{cityName}/list-all-page")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<WheaterDataEntity>> get(@PathVariable String cityName) throws IOException {
        Page<WheaterDataEntity> pageResult = wheaterDataService.findAllPageByName(cityName);

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @ApiResponse(description = "lista 10 registros por página, quando NÃO pesquisar uma cidade.")
    @GetMapping("/list-all-page")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<WheaterDataEntity>> get() throws IOException {
        Page<WheaterDataEntity> pageResult = wheaterDataService.findAllPage();

        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }

    @PutMapping("/{idWheaterData}")
    public ResponseEntity<WheaterDataRequestDTO> put(@PathVariable(value = "idWheaterData") Long idWheaterData, @RequestBody WheaterDataRequestDTO wheaterDataRequestDTO) throws IOException{
        WheaterDataEntity wheaterDataEntity = new WheaterDataEntity();

        BeanUtils.copyProperties(wheaterDataRequestDTO, wheaterDataEntity);
        WheaterDataEntity updateWheaterDataEntity = wheaterDataService.update(idWheaterData, wheaterDataEntity);
        wheaterDataRequestDTO.setIdWheaterData(updateWheaterDataEntity.getIdWheaterData());

        return ResponseEntity.ok(wheaterDataRequestDTO);
    }

    @DeleteMapping("/{idWheaterData}")
    public ResponseEntity<Void> delete(@PathVariable(value = "idWheaterData") Long idWheaterData) throws IOException{
        wheaterDataService.deleteById(idWheaterData);
        return ResponseEntity.noContent().build();
    }
}
