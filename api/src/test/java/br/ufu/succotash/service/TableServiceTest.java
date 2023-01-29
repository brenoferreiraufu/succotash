package br.ufu.succotash.service;

import br.ufu.succotash.domain.model.Table;
import br.ufu.succotash.repository.TableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TableServiceTest {

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;

    @Test
    public void findTableByRestaurant_shouldReturnTables() {
        String restaurantName = "Succotash";
        List<Table> expectedTables = Arrays.asList(new Table(), new Table());
        when(tableRepository.findByRestaurantName(restaurantName)).thenReturn(expectedTables);

        List<Table> actualTables = tableService.findTableByRestaurant();

        verify(tableRepository).findByRestaurantName(restaurantName);
        assertEquals(expectedTables, actualTables);
    }

    @Test
    public void findTableById_shouldReturnTable() {
        String tableId = "tableId";
        Table expectedTable = new Table();
        when(tableRepository.findById(tableId)).thenReturn(Optional.of(expectedTable));

        Table actualTable = tableService.findTableById(tableId);

        verify(tableRepository).findById(tableId);
        assertEquals(expectedTable, actualTable);
    }

    @Test
    public void findTableById_shouldThrowExceptionWhenTableNotFound() {
        String tableId = "tableId";
        when(tableRepository.findById(tableId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> tableService.findTableById(tableId));
    }
}
