package dat.security.daos;

import dat.security.entities.User;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;

import java.util.Set;

public interface ISecurityDAO {
    UserDTO getVerifiedUser(String username, String password) throws ValidationException;
    User createUser(String username, String password);
    User addRole(UserDTO user, String newRole);
    Set<User> getAllUsers();
    void updateUserPassword(String username, String newPassword);
    void deleteUser(String username);
    void updateUserRole(String username, String role);
}
