package br.ufu.succotash.repository;

import br.ufu.succotash.domain.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, String> {}
