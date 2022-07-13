package br.ufu.succotash.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @ManyToOne private User user;
    @NotNull @ManyToOne private Table table;
    @NotNull @Enumerated(EnumType.STRING) private OrderStatus status;
    @NotNull private final LocalDateTime createdAt = LocalDateTime.now();
    @NotNull private LocalDateTime updatedAt = LocalDateTime.now();
    @NotNull @ManyToMany private List<Item> items;

    private Order() {}

    public Order(User user, Table table, OrderStatus status, List<Item> items) {
        this.user = user;
        this.table = table;
        this.status = status;
        this.items = items;
        this.updatedAt = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.updatedAt = LocalDateTime.now();
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
        this.updatedAt = LocalDateTime.now();
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        this.updatedAt = LocalDateTime.now();
    }
}
