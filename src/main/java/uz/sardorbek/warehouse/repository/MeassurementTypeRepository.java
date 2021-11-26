package uz.sardorbek.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.sardorbek.warehouse.entity.MeassurementType;

public interface MeassurementTypeRepository extends JpaRepository<MeassurementType,Long> {
    MeassurementType findByName(String name);
}
