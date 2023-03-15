package ssm.service;

import ssm.pojo.Admin;

public interface AdminService {
    //完成登录判断
    Admin login(String username, String password);
}
