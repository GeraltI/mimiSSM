package ssm.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ssm.pojo.ProductType;
import ssm.service.ProductTypeService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

/**
 * @author xiao hao
 */
@WebListener
public class ProductTypeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        //不能确定监听器创建顺序在Spring前后
        //手动从Spring容器中获取ProductTypeServiceImpl对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = applicationContext.getBean("productTypeServiceImpl",ProductTypeService.class);
        List<ProductType> typeList = productTypeService.getAllProductType();
        //放入全局页面作用域中，供新增页面，修改页面，前台的查询功能提供全部的商品类别集合
        servletContextEvent.getServletContext().setAttribute("typeList",typeList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
