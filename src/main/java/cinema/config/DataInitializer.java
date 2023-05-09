package cinema.config;

import cinema.model.Role;
import cinema.model.User;
import cinema.service.RoleService;
import cinema.service.UserService;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private final Environment environment;
    private final RoleService roleService;
    private final UserService userService;

    public DataInitializer(Environment environment,
                           RoleService roleService,
                           UserService userService) {
        this.environment = environment;
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Role adminRole = new Role();
        adminRole.setName(Role.RoleName.ADMIN);
        roleService.add(adminRole);
        Role userRole = new Role();
        userRole.setName(Role.RoleName.USER);
        roleService.add(userRole);

        User admin = new User();
        admin.setEmail(environment.getProperty("security.admin.login"));
        admin.setPassword(environment.getProperty("security.admin.password"));
        admin.setRoles(Set.of(adminRole));
        userService.add(admin);

        User user = new User();
        user.setEmail(environment.getProperty("security.user.login"));
        user.setPassword(environment.getProperty("security.user.password"));
        user.setRoles(Set.of(userRole));
        userService.add(user);
    }
}
