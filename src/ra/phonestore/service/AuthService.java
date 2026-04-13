package ra.phonestore.service;

import ra.phonestore.dao.UserDAO;
import ra.phonestore.model.User;
import ra.phonestore.util.PasswordUtil;

public class AuthService {
    private UserDAO userDAO = new UserDAO();

    public boolean register(User user) {
        user.setPassword(PasswordUtil.hashPassword(user.getPassword()));
        user.setRole("CUSTOMER");
        return userDAO.insert(user);
    }

    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }
}