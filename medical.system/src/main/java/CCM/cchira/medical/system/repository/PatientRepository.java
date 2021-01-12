package CCM.cchira.medical.system.repository;

import CCM.cchira.medical.system.entity.Caregiver;
import CCM.cchira.medical.system.entity.Doctor;
import CCM.cchira.medical.system.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    @Query(value = "SELECT p " +
            "FROM Patient p " +
            "ORDER BY p.lastName")
    List<Patient> getAllOrdered();

    List<Patient> findAllByDoctor(Doctor doctor);

    List<Patient> findAllByCaregiver(Caregiver caregiver);
}
