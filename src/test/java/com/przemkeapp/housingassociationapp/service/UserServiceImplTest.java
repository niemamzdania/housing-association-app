package com.przemkeapp.housingassociationapp.service;

import com.przemkeapp.housingassociationapp.Entity.User;
import com.przemkeapp.housingassociationapp.dao.UserDao;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserDao userDaoMock;

    @Autowired
    UserDao userDao;

    @Test
    void findingUserByUsernameShouldReturnUser() {
        List<User> users = userDao.findAllUsers();
        System.out.println("TEST - LIST OF USERS");
        for (User u : users) {
            System.out.println(u.getUserName());
        }
        User user = new UserServiceImpl(userDao).findUserByUsername("not_przemke");
        assertNotNull(user);
    }

    @Test
    void findingUserNameTestShouldReturnNull() {
        // given
        when(userDaoMock.findUserByUsername("test")).thenReturn(null);
        String userName = "test";
        // when
        User user = new UserServiceImpl(userDaoMock).findUserByUsername(userName);
        // then
        assertNull(user);
    }
}