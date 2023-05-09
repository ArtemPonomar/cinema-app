package cinema.dao;

import cinema.model.Role;
import java.util.List;
import java.util.Optional;

public interface RoleDao {
    Role add(Role role);

    Optional<Role> get(Long id);

    Optional<Role> getByName(Role.RoleName name);

    List<Role> getAll();
}
