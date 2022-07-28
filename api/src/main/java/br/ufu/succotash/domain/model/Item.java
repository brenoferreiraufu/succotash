package br.ufu.succotash.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Entity(name = "items")
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    @NotNull
    private String image;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private BigDecimal price;
    @NotNull @ManyToOne(cascade = CascadeType.ALL)
    private Restaurant restaurant;

}
