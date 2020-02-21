package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;

  //根据用户名查询数据 获取用户信息和用户关联的角色信息,角色关联的权限
    public User findByUsername(String username) {
        User user = userDao.findByUsernme(username);
        if(user==null){
            return null;
        }
        Integer userId = user.getId();
        //根据用户id查询角色
        Set<Role> roles = roleDao.findByUserId(userId);
        for (Role role : roles) {
            //根据角色id查询权限
            Integer roleId = role.getId();
            Set<Permission> permissions = permissionDao.findByRoleId(roleId);
            role.setPermissions(permissions);//角色关联权限
        }
        user.setRoles(roles);//让用户关联角色

        return user;
    }
}
