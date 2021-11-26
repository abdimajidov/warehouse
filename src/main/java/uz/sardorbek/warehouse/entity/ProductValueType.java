package uz.sardorbek.warehouse.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductValueType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "meassurement_type_id", foreignKey = @ForeignKey(name = "meassurement_type_id"))
    MeassurementType meassurementType;

    @ManyToOne
    @JoinColumn(name = "meassurement_value_id", foreignKey = @ForeignKey(name = "meassurement_value_id"))
    MeassurementValue meassurementValue;

    @ManyToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "product_id"))
    Product product;

    public ProductValueType(MeassurementType meassurementType, MeassurementValue meassurementValue, Product product) {
        this.meassurementType = meassurementType;
        this.meassurementValue = meassurementValue;
        this.product = product;
    }
}
