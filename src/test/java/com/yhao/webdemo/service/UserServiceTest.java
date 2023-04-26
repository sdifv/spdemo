package com.yhao.webdemo.service;

import com.yhao.webdemo.common.MockUtil;

class UserServiceTest {

    UserService userService = MockUtil.innerMockObject(UserService.class);

//    @Test
//    void getUserById() {
//        UserDao userDao = userService.getUserDao();
//        Mockito.doReturn(Optional.of(new UserPO("a", "123"))).when(userDao).findById(1);
//        UserPO user = userService.getUserById(1);
//        Assertions.assertNotNull(user);
//        Assertions.assertEquals(user.getName(), "a");
//    }
//
//    @Test
//    void listUser() {
//        UserDao userDao = userService.getUserDao();
//        List<UserPO> userList = Arrays.asList(
//                new UserPO("a", "123"),
//                new UserPO("b", "123"),
//                new UserPO("c", "123"));
//        Mockito.doReturn(userList).when(userDao).findAll();
//        List<UserPO> listUser = userService.listUser();
//        Assertions.assertNotNull(listUser);
//        Assertions.assertEquals("b", listUser.get(1).getName());
//    }
}