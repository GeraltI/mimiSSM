package ssm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ssm.pojo.Admin;
import ssm.service.AdminService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiao hao
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {

    //在所有的界面层，一定会有业务逻辑层的对象

    @Autowired
    private AdminService adminService;

    //实现登陆判断，并进行相应的跳转
    @RequestMapping("/login")
    public String login(String name, String pwd, HttpServletRequest request)
    {
        Admin admin = adminService.login(name, pwd);
        //登陆成功
        if(admin != null)
        {
            request.setAttribute("admin",admin);
            return "main";
        }
        //登陆失败
        else{
            request.setAttribute("errmsg","用户或者密码不正确");
            return "login";
        }
    }

}
