package lk.ijse.agms.automation_service.repo;

import lk.ijse.agms.automation_service.entity.AutomationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomationLogRepository extends JpaRepository<AutomationLog, Long> {
}
