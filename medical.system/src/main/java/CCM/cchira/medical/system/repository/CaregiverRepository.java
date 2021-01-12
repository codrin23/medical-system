package CCM.cchira.medical.system.repository;

import CCM.cchira.medical.system.entity.Caregiver;
import CCM.cchira.medical.system.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.PreRemove;
import java.util.List;
import java.util.Optional;

@Repository
public interface CaregiverRepository extends JpaRepository<Caregiver, Integer> {
    @Query(value = "SELECT c " +
            "FROM Caregiver c " +
            "ORDER BY c.lastName")
    List<Caregiver> getAllOrdered();

    @PreRemove
    void deleteCaregiverByEmail(String email);

    Optional<Caregiver> findById(Integer id);
}
