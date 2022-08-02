package br.ufu.succotash.service;

import br.ufu.succotash.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TableService {

    private final TableRepository tableRepository;
}
