package lk.ijse.agms.zone_service.repo;

import lk.ijse.agms.zone_service.entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneRepository extends JpaRepository<Zone,String> {
}
