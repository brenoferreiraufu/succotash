package br.ufu.succotash.service;

import br.ufu.succotash.domain.model.Table;
import br.ufu.succotash.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public List<Table> findTableByRestaurant() {
        return tableRepository.findByRestaurantName("Succotash");
    }

    public Table findTableById(String tableId) {
        return tableRepository.findById(tableId).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        });
    }
}
