package uz.sardorbek.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import uz.sardorbek.warehouse.entity.ProductValueType;

import javax.transaction.Transactional;

public interface ProductValueTypeRepository extends JpaRepository<ProductValueType, Long> {
    ProductValueType findByProductId(Long product_id);

    @Modifying
    @Transactional
    void deleteByProductId(Long id);
}
