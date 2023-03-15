package ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.mapper.AdminMapper;
import ssm.pojo.Admin;
import ssm.pojo.AdminExample;
import ssm.service.AdminService;
import ssm.utils.MD5Util;

import java.util.List;

/**
 * @author xiao hao
 */
@Service
public class AdminServiceImpl implements AdminService {

    //业务逻辑层中的数据访问对象

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {

        //根据传入的用户到数据库中查询相应用户对象

        //如果有条件，需要使用adminExample进行查询
        AdminExample adminExample = new AdminExample();
        /**
         * 添加条件
         * select * from admin where a_name = "admin"
         */
        adminExample.createCriteria().andANameEqualTo(name);
        List<Admin> adminList = adminMapper.selectByExample(adminExample);
        if(adminList.size() == 1)
        {
            //如果查询到用户对象，再进行密码的比对，
            //将密码利用MD5密文加密，再继续比对(MD5密文加密不可逆)
            Admin admin = adminList.get(0);
            String miPwd = MD5Util.getMD5(pwd);
            if(miPwd.equals(admin.getaPass()))
            {
                return admin;
            }
        }
        return null;
    }

}
