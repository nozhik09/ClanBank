package repository;

import model.BankAccount;
import model.Role;
import model.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UsersRepository {
    private Map<Integer, Users> usersMap;

    public UsersRepository() {
        this.usersMap = new HashMap<>();
        initUser();
    }
    private void initUser() {
        Users adminUser = new Users(1, "admin123", "Vasya@gmail.com", "Kakasss1223!", Role.ADMIN);
        Users user2 = new Users(2, "user133", "Vasya@gm3il.com", "Kaka54s1223!", Role.USER);
        Users user3 = new Users(3, "user143", "Vasya@gm2mail.com", "Kaka35s1223!", Role.USER);

        addUser(adminUser);
        addUser(user2);
        addUser(user3);
    }

    private void addUser(Users user) {
        int id = user.getId();
        user.setId(id);
        usersMap.put(id, user);
    }
    public Users getUserById(int id) {
        return usersMap.get(id);
    }
    public Users findUserByEmail(String email) {
        for (Users user : usersMap.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }
    public void deleteUser(int id) {
        usersMap.remove(id);
    }

    public void updateUser(Users newUser) {
        int newUserId = newUser.getId();
        Users user = getUserById(newUserId);
            user.setName(newUser.getName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            user.setRole(newUser.getRole());

            usersMap.put(newUserId, user);
        }


public List<Users> getAllUsers() {
    return new ArrayList<>(usersMap.values());
}



}

