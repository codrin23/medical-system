package CCM.cchira.medical.system.repository;

import CCM.cchira.medical.system.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query(value = "SELECT d " +
            "FROM Doctor d " +
            "ORDER BY d.lastName")
    List<Doctor> getAllOrdered();
}
