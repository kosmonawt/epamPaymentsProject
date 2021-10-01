package service;

import dto.UserDTO;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserServiceTest {

    final UserService userService = UserService.getInstance();
    final UserDTO userDTO = new UserDTO();


    @Before
    public void setUP() {

        userDTO.setEmail("c@c.c");
        userDTO.setPassword("qazwsx");
        userDTO.setRole("USER");

    }

    /**
     * et user from db by email test
     */
    @Test
    public void getUserByEmailTest() {

        String email = "c@c.c";
        UserDTO userDB = userService.getUserByEmail(email);

        assertEquals(userDTO.getEmail(), userDB.getEmail());
        assertEquals(userDTO.getPassword(), userDB.getPassword());

    }


    /**
     * Get empty user DTO from db when user not present in database test
     */
    @Test
    public void getEmptyUserDTOWhenUserNotPresentInDBTest() {

        String email = "ascsacsac@ascac.gfnfgnfgn";
        UserDTO userDB = userService.getUserByEmail(email);
        assertNull(userDB.getEmail());
        assertNull(userDB.getPassword());
    }

    /**
     * Return true if given user password match with user password in database
     */

    @Test
    public void returnTrueIfUserPasswordMatchTest() {
        String email = "c@c.c";
        String password = "qazwsx";
        assertTrue(userService.find(email, password));
    }

    /**
     * Return false if given user password didn't match with user password in database
     */

    @Test
    public void returnFalseIfUserPasswordDidntMatchTest() {
        String email = "c@c.c";
        String password = "facavsv";
        assertFalse(userService.find(email, password));
    }

    @Test
    public void returnFalseIfUserNotPresentInDBWithPasswordTest() {
        String email = "afa@asfasf.saf";
        String password = "facavsv";
        assertFalse(userService.find(email, password));
    }

    /**
     * Return false if  user with email don't exist in database
     */

    @Test
    public void returnFalseIfUserNotPresentInDBTest() {
        String email = "acascasc@glgvhbhsaasc.khabvakssk";
        assertFalse(userService.existsByEmail(email));
    }


    @Test
    public void returnTrueIfUserPresentInDBTest() {
        String email = "c@c.c";
        assertTrue(userService.existsByEmail(email));
    }


}
