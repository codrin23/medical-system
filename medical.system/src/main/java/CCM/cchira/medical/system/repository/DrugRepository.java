package CCM.cchira.medical.system.repository;

import CCM.cchira.medical.system.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DrugRepository extends JpaRepository<Drug, Integer> {
    @Query(value = "SELECT d " +
            "FROM Drug d " +
            "ORDER BY d.name")
    List<Drug> getAllOrdered();

    Optional<Drug> findByName(String s);
}
