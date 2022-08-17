package br.ufu.succotash.service;

import br.ufu.succotash.controller.table.request.TableRequest;
import br.ufu.succotash.domain.model.Table;
import br.ufu.succotash.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public String newTable(TableRequest table) {
        return tableRepository.save(table.toModel()).getId();
    }

    public List<Table> findTableByRestaurant() {
        return tableRepository.findByRestaurantName("Succotash");
    }


}
