package cinema.service.impl;

import cinema.dao.ShoppingCartDao;
import cinema.dao.UserDao;
import cinema.model.ShoppingCart;
import cinema.model.User;
import cinema.service.UserService;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder encoder;
    private final UserDao userDao;
    private final ShoppingCartDao shoppingCartDao;

    public UserServiceImpl(PasswordEncoder encoder,
                           UserDao userDao,
                           ShoppingCartDao shoppingCartDao) {
        this.encoder = encoder;
        this.userDao = userDao;
        this.shoppingCartDao = shoppingCartDao;
    }

    @Override
    public User add(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        shoppingCartDao.add(new ShoppingCart(user));
        return user;
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).orElseThrow(
                () -> new RuntimeException("User with id " + id + " not found"));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
