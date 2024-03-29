package br.ufu.succotash.domain.model;

import br.ufu.succotash.domain.enumeration.TableStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "tables")
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String urlQrCode;

    @ManyToOne()
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TableStatus status;

    public Table(String name, String urlQrCode, Restaurant restaurant, TableStatus status) {
        this.name = name;
        this.urlQrCode = urlQrCode;
        this.restaurant = restaurant;
        this.status = status;
    }
}
