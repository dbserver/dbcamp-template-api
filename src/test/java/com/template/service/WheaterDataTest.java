package com.template.service;

import com.template.business.services.CityService;
import com.template.business.services.WheaterDataService;
import com.template.data.entity.CityEntity;
import com.template.data.entity.WheaterDataEntity;
import com.template.data.entity.enums.DayTimeEnum;
import com.template.data.entity.enums.NightTimeEnum;
import com.template.data.repository.WheaterDataRepository;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Assertions;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class WheaterDataTest {

    @Mock
    WheaterDataEntity wheaterDataEntiytMock;

    @Mock
    WheaterDataRepository wheaterDataRepositoryMock;

    @Mock
    CityService cityServiceMock;

    @InjectMocks
    WheaterDataService wheaterDataServiceMock;

    public WheaterDataEntity wheaterDataEntityLis1tMock() {
        return new WheaterDataEntity(602L,
                new CityEntity(202L, "Porto Alegre"),
                LocalDate.of(2023, 4, 28),
                DayTimeEnum.SOL_COM_NUVENS,
                NightTimeEnum.NUBLADA,
                22,
                15,
                6,
                26,
                10
        );
    }

    public WheaterDataEntity wheaterDataEntityLis2tMock() {
        return new WheaterDataEntity(403L,
                new CityEntity(203L, "Gramado"),
                LocalDate.of(2023, 3, 31),
                DayTimeEnum.NUBLADO,
                NightTimeEnum.NEVE,
                25,
                19,
                9,
                30,
                5
        );
    }

    @Test
    void saveSuccessfully() {
        // Arrange
        when(wheaterDataRepositoryMock.save(wheaterDataEntiytMock)).thenReturn(wheaterDataEntiytMock);

        // Assert
        Assertions.assertEquals(wheaterDataEntiytMock, wheaterDataRepositoryMock.save(wheaterDataEntiytMock));
        Mockito.verify(wheaterDataRepositoryMock, times(1)).save(wheaterDataEntiytMock);
    }

    @Test
    void saveFailure() {
        // Arrange
        when(wheaterDataRepositoryMock.save(wheaterDataEntiytMock)).thenThrow(new RuntimeException("Erro ao salvar os dados"));

        // Assert
        assertThrows(RuntimeException.class, () -> {
            wheaterDataServiceMock.save(wheaterDataEntiytMock);
        });
    }

    @SneakyThrows
    @Test
    void findAllSuccessfully() {
        // Arrange
        WheaterDataEntity wheaterDataEntityList1 = wheaterDataEntityLis1tMock();
        WheaterDataEntity wheaterDataEntityList2 = wheaterDataEntityLis2tMock();

        when(wheaterDataRepositoryMock.findAllByOrderByDateDesc()).thenReturn(List.of(wheaterDataEntityList1, wheaterDataEntityList2));

        // Act
        List<WheaterDataEntity> wheaterDataList = wheaterDataServiceMock.findAll();

        // Assert
        Assertions.assertEquals(2, wheaterDataList.size());
        Assertions.assertEquals(wheaterDataEntityList1, wheaterDataList.get(0));
        Assertions.assertEquals(wheaterDataEntityList2, wheaterDataList.get(1));
    }

    @SneakyThrows
    @Test
    void findAllFailure() {
        // Arrange
        when(wheaterDataRepositoryMock.findAllByOrderByDateDesc()).thenThrow(new RuntimeException("Erro ao retornar os dados"));

        // Assert
        assertThrows(RuntimeException.class, () -> {
            wheaterDataServiceMock.findAll();
        });
    }

    @SneakyThrows
    @Test
    void findAllByNameSuccessfully()  {
        // Arrange
        String name = "Porto Alegre";

        WheaterDataEntity wheaterDataEntityName1 = new WheaterDataEntity(1L, new CityEntity(201L, "Porto Alegre"),
                LocalDate.of(2023, 4, 20),
                DayTimeEnum.NUBLADO,
                NightTimeEnum.NUBLADA,
                19,
                10,
                6,
                26,
                10);

        WheaterDataEntity wheaterDataEntityName2 = new WheaterDataEntity(2L, new CityEntity(202L, "Porto Alegre"),
                LocalDate.of(2023, 4, 10),
                DayTimeEnum.SOL_COM_NUVENS,
                NightTimeEnum.CHUVA,
                25,
                17,
                8,
                70,
                17);

        Sort sort = Sort.by("date").descending();
        when(wheaterDataRepositoryMock.findAllByCityNameIgnoreCase(name, sort)).thenReturn(List.of(wheaterDataEntityName1, wheaterDataEntityName2));

        // Act
        List<WheaterDataEntity> result = wheaterDataServiceMock.findAllByName(name, sort);

        // Assert
        for (WheaterDataEntity entity : result) {
            assertEquals(name, entity.getCity().getName());
        }
    }

    @Test
    void findAllByNameFailure()  {
        // Arrange
        String name = "São Paulo";
        Sort sort = Sort.by("date").descending();

        // Act
        when(wheaterDataRepositoryMock.findAllByCityNameIgnoreCase(eq(name), any(Sort.class)))
                .thenThrow(new DataAccessException("Erro ao executar consulta no banco de dados") {});

        // Assert
        assertThrows(DataAccessException.class, () -> {
            wheaterDataServiceMock.findAllByName(name, sort);
        });
    }

    @SneakyThrows
    @Test
    void findByDateBetweenSuccessfully() {
        // Arrange
        LocalDate today = LocalDate.now();
        LocalDate days = today.plusDays(6);
        String name = "Porto Alegre";
        Sort sort = Sort.by("date").ascending();

        List<WheaterDataEntity> entityList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            WheaterDataEntity entity = new WheaterDataEntity();
            entity.setDate(today.plusDays(i));
            entity.setCity(new CityEntity(202L, name));
            entityList.add(entity);
        }

        when(wheaterDataRepositoryMock.findByCityNameIgnoreCaseAndDateBetween(name, today, days, sort)).thenReturn(entityList);

        // Act
        List<WheaterDataEntity> resultList = wheaterDataServiceMock.findByDateBetween(name);

        // Assert
        for (int i = 0; i < 6; i++) {
            assertEquals(today.plusDays(i), resultList.get(i).getDate());
        }
    }

    @Test
    public void findByDateBetweenFailure() {
        // Arrange
        LocalDate today = LocalDate.now();
        LocalDate days = today.plusDays(6);
        String name = "Porto Alegre";
        Sort sort = Sort.by("date").ascending();

        when(wheaterDataRepositoryMock.findByCityNameIgnoreCaseAndDateBetween(eq(name), eq(today), eq(days), any(Sort.class)))
                .thenThrow(new RuntimeException("Failed to connect to database"));

        // Act
        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            wheaterDataServiceMock.findByDateBetween(name);
        });

        // Assert
        assertEquals("Failed to connect to database", throwable.getMessage());
    }

    @SneakyThrows
    @Test
    public void updateSuccessfully() {
        //Arrange
        Long id = 1001L;
        CityEntity city = new CityEntity(202L, "Porto Alegre");
        LocalDate date = LocalDate.of(2023, 5, 1);
        DayTimeEnum dayTime = DayTimeEnum.CHUVA;
        NightTimeEnum nightTime = NightTimeEnum.TEMPESTADE;
        int maxTemperature = 25;
        int minTemperature = 14;
        int windSpeed = 8;
        int humidity = 60;
        int precipitation = 1020;

        when(cityServiceMock.findById(city.getIdCity())).thenReturn(Optional.of(city));

        WheaterDataEntity entity = new WheaterDataEntity(id, city, date, dayTime, nightTime,
                maxTemperature, minTemperature, windSpeed, humidity, precipitation);

        when(wheaterDataRepositoryMock.findById(entity.getIdWheaterData())).thenReturn(Optional.of(entity));
        when(wheaterDataRepositoryMock.save(entity)).thenReturn(entity);

        // Act
        WheaterDataEntity updatedEntity = wheaterDataServiceMock.update(id, entity);

        // Assert
        assertEquals(id, updatedEntity.getIdWheaterData());
        assertEquals(city, updatedEntity.getCity());
        assertEquals(date, updatedEntity.getDate());
        assertEquals(dayTime, updatedEntity.getDayTimeEnum());
        assertEquals(nightTime, updatedEntity.getNightTimeEnum());

        Optional<Integer> maxTemperatureOptional = Optional.ofNullable(maxTemperature);
        Optional<Integer> maxTemperatureOptionalUpdatedEntity = Optional.ofNullable(maxTemperature);
        assertEquals(maxTemperatureOptional.map((Integer::valueOf)), maxTemperatureOptionalUpdatedEntity.map(Integer::valueOf));

        Optional<Integer> minTemperatureOptional = Optional.ofNullable(minTemperature);
        Optional<Integer> minTemperatureOptionalUpdatedEntity = Optional.ofNullable(minTemperature);
        assertEquals(minTemperatureOptional.map((Integer::valueOf)), minTemperatureOptionalUpdatedEntity.map(Integer::valueOf));

        Optional<Integer> windSpeedOptional = Optional.ofNullable(windSpeed);
        Optional<Integer> windSpeedOptionalUpdatedEntity = Optional.ofNullable(windSpeed);
        assertEquals(windSpeedOptional.map((Integer::valueOf)), windSpeedOptionalUpdatedEntity.map(Integer::valueOf));

        Optional<Integer> humiditySpeedOptional = Optional.ofNullable(humidity);
        Optional<Integer> humidityOptionalUpdatedEntity = Optional.ofNullable(humidity);
        assertEquals(humiditySpeedOptional.map((Integer::valueOf)), humidityOptionalUpdatedEntity.map(Integer::valueOf));

        Optional<Integer> precipitationOptional = Optional.ofNullable(precipitation);
        Optional<Integer> precipitationOptionalUpdatedEntity = Optional.ofNullable(precipitation);
        assertEquals(precipitationOptional.map((Integer::valueOf)), precipitationOptionalUpdatedEntity.map(Integer::valueOf));
    }

    @Test
    public void updateFailure() {
        // Arrange
        Long idWheaterData = 100L;
        WheaterDataEntity wheaterDataEntityList1 = wheaterDataEntityLis1tMock();

        // Act
        when(wheaterDataRepositoryMock.findById(idWheaterData))
                .thenThrow(new DataAccessException("Erro ao executar consulta no banco de dados") {});

        // Assert
        assertThrows(DataAccessException.class, () -> {
            wheaterDataServiceMock.update(idWheaterData, wheaterDataEntityList1);
        });
    }

    @SneakyThrows
    @Test
    void deleteSuccessfully() {
        // Arrange
        wheaterDataServiceMock.deleteById(403L);

        // Assert
        Mockito.verify(wheaterDataRepositoryMock, times(1)).deleteById(403L);
    }

    @Test
    void deleteFailure() {
        // Arrange
        Long id = 403L;
        doThrow(new DataIntegrityViolationException("Erro ao excluir os dados"))
                .when(wheaterDataRepositoryMock).deleteById(id);

        // Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            wheaterDataServiceMock.deleteById(id);
        });
    }
}
