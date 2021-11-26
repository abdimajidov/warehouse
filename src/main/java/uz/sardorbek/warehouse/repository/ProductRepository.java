package uz.sardorbek.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.sardorbek.warehouse.entity.Product;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);

    @Modifying(clearAutomatically = true)
    @Transactional
    void deleteByName(String name);

    @Query(value = "select * from product where storage_date=:value",nativeQuery = true)
    List<Product> searchBySDate(@Param("value") String value);

    @Query(value = "select * from product where price=:value",nativeQuery = true)
    List<Product> searchByPrice(@Param("value") String value);

    @Query(value = "select * from product\n" +
            "join product_value_type pvt on product.id = pvt.product_id\n" +
            "join meassurement_type mt on pvt.meassurement_type_id = mt.id\n" +
            "where mt.name=:value",nativeQuery = true)
    List<Product> searchByMType(@Param("value") String value);

    @Query(value = "select * from product\n" +
            "join product_value_type pvt on product.id = pvt.product_id\n" +
            "join meassurement_value mv on mv.id = pvt.meassurement_value_id\n" +
            "where mv.value=:value",nativeQuery = true)
    List<Product> searchByMValue(@Param("value") String value);
}
