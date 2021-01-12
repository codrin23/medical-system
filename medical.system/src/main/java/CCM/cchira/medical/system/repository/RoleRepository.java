package CCM.cchira.medical.system.repository;

import CCM.cchira.medical.system.entity.Role;
import CCM.cchira.medical.system.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
