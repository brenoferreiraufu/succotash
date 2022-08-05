package br.ufu.succotash.service;

import br.ufu.succotash.controller.table.request.TableRequest;
import br.ufu.succotash.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;

    public String newTable(TableRequest table) {
        return tableRepository.save(table.toModel()).getId();
    }

    //public Optional<User> findUser(String userId) {
    //    return userRepository.findById(userId);
    //}


}
