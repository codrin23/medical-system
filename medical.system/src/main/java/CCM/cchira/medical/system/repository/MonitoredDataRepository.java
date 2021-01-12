package CCM.cchira.medical.system.repository;

import CCM.cchira.medical.system.entity.MonitoredData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredDataRepository extends JpaRepository<MonitoredData, Integer> {

}
