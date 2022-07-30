package br.ufu.succotash.repository;

import br.ufu.succotash.domain.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {}
