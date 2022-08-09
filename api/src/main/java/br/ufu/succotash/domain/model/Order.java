package br.ufu.succotash.domain.model;

import br.ufu.succotash.domain.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity(name = "orders")
@Builder
@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.COMPLETED;

    @NotNull
    private final LocalDateTime createdAt = LocalDateTime.now();

    @NotNull
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Order(User user, Table table) {
        this.user = user;
        this.table = table;
        this.status = OrderStatus.COMPLETED;
    }
}
