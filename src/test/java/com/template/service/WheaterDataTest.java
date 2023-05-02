package com.template.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WheaterDataTest {

    @Mock
    WheaterDataEntity wheaterDataEntiytMock;

    @Mock
    WheaterDataRepository wheaterDataRepositoryMock;

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
    void saveFail() {
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
    void findAllFail() {
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

        Sort sort = Sort.by("date").ascending();
        when(wheaterDataRepositoryMock.findAllByCityName(name, sort)).thenReturn(List.of(wheaterDataEntityName1, wheaterDataEntityName2));

        // Act
        List<WheaterDataEntity> result = wheaterDataServiceMock.findAllByName(name, sort);

        // Assert
        for (WheaterDataEntity entity : result) {
            assertEquals(name, entity.getCity().getName());
        }

    }

    @Test
    void findAllByNameFail()  {
        String name = "São Paulo";
        Sort sort = Sort.by("date").descending();
        when(wheaterDataRepositoryMock.findAllByCityName(eq(name), any(Sort.class)))
                .thenThrow(new DataAccessException("Erro ao executar consulta no banco de dados") {});

        // When/Then
        assertThrows(DataAccessException.class, () -> {
            wheaterDataServiceMock.findAllByName(name, sort);
        });
    }

    @SneakyThrows
    @Test
    void findDateCurrentSuccessfully() {
        // Arrange
        LocalDate today = LocalDate.now();
        String name = "Porto Alegre";

        WheaterDataEntity wheaterDataEntityList1 = wheaterDataEntityLis1tMock();

        when(wheaterDataRepositoryMock.findByDateAndCityName(today, name)).thenReturn(List.of(wheaterDataEntityList1));

        // Act
        List<WheaterDataEntity> resultList = wheaterDataServiceMock.findDateCurrent(today, name);

        // Assert
        assertEquals(name, resultList.get(0).getCity().getName());
    }

    @Test
    void findDateCurrentFail() {
        // Arrange
        LocalDate date = LocalDate.now();
        String name = "Rio de Janeiro";

        when(wheaterDataRepositoryMock.findByDateAndCityName(eq(date), eq(name)))
                .thenThrow(new DataAccessException("Erro ao executar consulta no banco de dados") {});

        // Assert
        assertThrows(DataAccessException.class, () -> {
            wheaterDataServiceMock.findDateCurrent(date, name);
        });
    }

    @SneakyThrows
    @Test
    void findAllNextDaysSuccessfull() {
        // Arrange
        LocalDate date =  LocalDate.of(2023, 4, 20);
        WheaterDataEntity wheaterDataEntityName1 = new WheaterDataEntity(1L, new CityEntity(201L, "Porto Alegre"),
                date,
                DayTimeEnum.NUBLADO,
                NightTimeEnum.NUBLADA,
                19,
                10,
                6,
                26,
                10);

        LocalDate today = LocalDate.now();
        int days = 7;
        Sort sort = Sort.by("date").ascending();
        String name = "Gramado";

        List<WheaterDataEntity> entityList = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            WheaterDataEntity entity = new WheaterDataEntity();
            entity.setDate(today.plusDays(i));
            entity.setCity(new CityEntity(1L, name));
            entityList.add(entity);
        }

        when(wheaterDataRepositoryMock.findTop7ByCityNameOrderByDateAsc(eq(name), any(Sort.class)))
                .thenReturn(entityList);

        // Act
        List<WheaterDataEntity> resultList = wheaterDataServiceMock.findAllNextDays(name, sort);

        // Assert
        assertEquals(days, resultList.size());
        for (int i = 0; i < days; i++) {
            assertEquals(today.plusDays(i), resultList.get(i).getDate());
        }
    }

    @Test
    public void findAllNextDaysFail() {
        // Arrange
        Sort sort = Sort.by("date").ascending();
        String name = "Gramado";

        when(wheaterDataRepositoryMock.findTop7ByCityNameOrderByDateAsc(eq(name), any(Sort.class)))
                .thenThrow(new RuntimeException("Failed to connect to database"));

        // Act
        Throwable throwable = assertThrows(RuntimeException.class, () -> {
            wheaterDataServiceMock.findAllNextDays(name, sort);
        });

        // Assert
        assertEquals("Failed to connect to database", throwable.getMessage());
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
    void deleteFail() {
        // Arrange
        Long id = 403L;
        doThrow(new DataIntegrityViolationException("Erro ao excluir"))
                .when(wheaterDataRepositoryMock).deleteById(id);

        // Assert
        assertThrows(DataIntegrityViolationException.class, () -> {
            wheaterDataServiceMock.deleteById(id);
        });
    }
}
