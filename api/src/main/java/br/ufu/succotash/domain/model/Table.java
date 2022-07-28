package br.ufu.succotash.model;

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

    @NotNull @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

    @NotNull @Enumerated(EnumType.STRING)
    private TableStatus status;

}
