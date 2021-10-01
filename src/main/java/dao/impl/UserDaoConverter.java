package dao.impl;

import dao.DaoConverter;
import dto.UserDTO;
import entity.Role;
import entity.Status;
import entity.User;

/**
 * Convert User entity to DTO and back
 */


public class UserDaoConverter implements DaoConverter<UserDTO, User> {

    @Override
    public User convertTo(UserDTO userDTO) {

        User user = new User();
        if (userDTO != null) {
            user.setName(userDTO.getName());
            user.setSurname(userDTO.getSurname());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            if (userDTO.getRole() != null) {
                user.setRole(Role.valueOf(userDTO.getRole().toUpperCase()));
            }
            user.setId(userDTO.getId());
            user.setStatus(Status.valueOf(userDTO.getStatus().trim().toUpperCase()));
        }
        return user;
    }


    @Override
    public UserDTO convertFrom(User user) {
        UserDTO userDTO = new UserDTO();
        if (user != null) {
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setSurname(user.getSurname());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setRole(user.getRole().name());
            userDTO.setStatus(user.getStatus().name());
        }
        return userDTO;
    }

}

