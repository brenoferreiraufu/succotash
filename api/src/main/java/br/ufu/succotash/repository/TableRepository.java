package br.ufu.succotash.repository;

import br.ufu.succotash.model.Table;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableRepository extends JpaRepository<Table, String> {}
