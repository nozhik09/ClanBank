/*
 * created by max$
 */


package service;

import model.Role;
import model.Users;
import repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UserService {

    private final UsersRepository userRepository;

    public UserService(UsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    private void getUserByEmail(String email) {
        userRepository.findUserByEmail(email);
    }


    private void addUser(Users user) {
        userRepository.addUser(user);
    }


    public void registerUser(String name, String email, String password) throws PasswordValidationException {
        if (userRepository.findUserByEmail(email) != null) {
            System.out.println("Пользователь с таким email уже существует.");
            return;
        }

        try {
            isValidEmail(email);
            isValidPassword(password);
        } catch (EmailValidateException e) {
            System.out.println("Неправильный формат email: " + e.getMessage());
            return;
        } catch (PasswordValidationException e) {
            System.out.println("Неправильный формат пароля: " + e.getMessage());
            return;
        }

        Users newUser = new Users(userRepository.userId.getAndIncrement(), name, email, password, Role.USER, new ArrayList<>());
        addUser(newUser);
        System.out.println("Пользователь успешно зарегистрирован.");
    }


    public static void isValidEmail(String email) throws EmailValidateException {
        // проверяем что пришел не null и не пустая строка
        if (email == null || email.isEmpty()) throw new EmailValidateException("Empty email");

        int indexAt = email.indexOf('@');
        if (indexAt <= 0 || indexAt != email.lastIndexOf('@')) throw new EmailValidateException("@ error");

        int indexFirstDotAfterAt = email.indexOf('.', indexAt);
        if (indexFirstDotAfterAt == -1 || indexFirstDotAfterAt == indexAt + 1)
            throw new EmailValidateException(". after @ error");

        if (email.lastIndexOf('.') >= email.length() - 2) throw new EmailValidateException("last dot error");

        boolean isCharAlphabetic = Character.isAlphabetic(email.charAt(0));
        if (!isCharAlphabetic) throw new EmailValidateException("illegal first symbol");

        for (int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);

            //true, if the symbol satisfies at least one of the criteria.
            boolean isCharValid = (
                    Character.isAlphabetic(c)
                            || Character.isDigit(c)
                            || c == '-'
                            || c == '_'
                            || c == '.'
                            || c == '@'
            );

            if (!isCharValid) throw new EmailValidateException("illegal symbol");

        }

        // All checks passed, nowhere did the method return false ->
    }


    public static void isValidPassword(String password) throws PasswordValidationException {
        //длина
        if (password.length() <= 8) {
            throw new PasswordValidationException("Длина маловата!");
        }

        boolean x = false;
        boolean y = false;
        boolean z = false;
        boolean k = false;

        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                x = true;
            }
        }

        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                y = true;
            }

        }
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                z = true;
            }

        }
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);

            boolean isCharValid = c == '!'
                    || c == '%'
                    || c == '$'
                    || c == '@'
                    || c == '&'
                    || c == '*'
                    || c == '('
                    || c == ')'
                    || c == '['
                    || c == ']';
            if (isCharValid == true) {
                k = true;
            }
        }
        if (x == false) {
            throw new PasswordValidationException("Надо хоть одну маленькую!");
        }
        if (y == false) {
            throw new PasswordValidationException("Надо хоть и одну большую!");
        }
        if (z == false) {
            throw new PasswordValidationException("А цифру?");
        }
        if (k == false) {
            throw new PasswordValidationException("И спецсимвол!");
        }
    }

    public Users authenticateUser(String email, String password) {
        Users user = userRepository.findUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Пользователь успешно авторизовался.");
            return userRepository.findUserByEmail(email);
        } else {
            System.out.println("Ошибка: неверный email или пароль.");
            return null;
        }
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteUser(email);
    }

    public List<Users> getAllUsers() {
        return userRepository.getAllUsers();
    }




}
