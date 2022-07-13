package br.ufu.succotash.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "tables")
public class Table {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull private String name;
    @NotNull private String urlQrCode;
    @NotNull @ManyToOne(cascade = CascadeType.ALL) private Restaurant restaurant;
    @NotNull @Enumerated(EnumType.STRING) private TableStatus status;

    private Table() {}

    public Table(String name, String urlQrCode, Restaurant restaurant, TableStatus status) {
        this.name = name;
        this.urlQrCode = urlQrCode;
        this.restaurant = restaurant;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlQrCode() {
        return urlQrCode;
    }

    public void setUrlQrCode(String urlQrCode) {
        this.urlQrCode = urlQrCode;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
