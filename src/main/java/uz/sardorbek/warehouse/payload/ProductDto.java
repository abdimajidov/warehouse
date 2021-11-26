package uz.sardorbek.warehouse.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PostPersist;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {
    Long number;


    String name;

    String price;

    LocalDate storageDate;

    String meassurementType;

    String meassurementValue;

    public ProductDto(String name, String price, LocalDate storageDate, String meassurementType, String meassurementValue) {
        this.name = name;
        this.price = price;
        this.storageDate = storageDate;
        this.meassurementType = meassurementType;
        this.meassurementValue = meassurementValue;
    }
}
