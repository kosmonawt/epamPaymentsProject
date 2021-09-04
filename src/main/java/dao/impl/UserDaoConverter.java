package dao.impl;

import dao.DaoConverter;
import dto.UserDTO;
import entity.Role;
import entity.User;

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
            } else if (!user.getId().equals(userDTO.getId()) && userDTO.getId() != null) {
                user.setId(userDTO.getId());
            }
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
            userDTO.setRole(user.getRole().name());
        }
        return userDTO;
    }

}

