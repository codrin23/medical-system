package CCM.cchira.medical.system.repository;

import CCM.cchira.medical.system.entity.MedicationPlan;
import CCM.cchira.medical.system.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationPlanRepository extends JpaRepository<MedicationPlan, Integer> {

    @Query(value = "SELECT m " +
            "FROM MedicationPlan m " +
            "WHERE m.patient = ?1" +
            "ORDER BY m.start DESC")
    public List<MedicationPlan> getAllOrdered(Patient patient);

    public List<MedicationPlan> findMedicationPlansByPatientOrderByStartDesc(Patient patient);

    public List<MedicationPlan> findAllByPatient(Patient patient);

    public List<MedicationPlan> getAllByPatient(Patient patient);
}
