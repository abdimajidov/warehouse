package uz.sardorbek.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sardorbek.warehouse.entity.MeassurementValue;

import java.util.Optional;

public interface MeassurementValueRepository extends JpaRepository<MeassurementValue,Long> {
    Optional<MeassurementValue> findByValue(String value);
}
