package ssm.controller;

import com.alibaba.druid.sql.dialect.postgresql.visitor.PGEvalVisitor;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ssm.pojo.ProductInfo;
import ssm.pojo.vo.ProductInfoVo;
import ssm.service.ProductInfoService;
import ssm.utils.FileNameUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author xiao hao
 */
@Controller
@RequestMapping("/product")
public class ProductInfoAction {

    //在所有的界面层，一定会有业务逻辑层的对象

    private static final int PAGE_SIZE = 5;

    String saveFileName = "";

    private ProductInfoVo vo;

    @Autowired
    private ProductInfoService productInfoService;

    @RequestMapping("/getALl")
    public String getAll(HttpServletRequest request)
    {
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list",list);
        return "product";
    }

    //显示第1页的数据

    @RequestMapping("/splitOne")
    public String splitOne(HttpServletRequest request)
    {
        //得到第1页的数据
        PageInfo info = productInfoService.splitPage(1,PAGE_SIZE);
        request.setAttribute("info",info);
        vo = new ProductInfoVo();
        saveFileName = "";
        return "product";
    }

    //显示第n页的数据

    @RequestMapping("/split")
    public String split(int page,HttpServletRequest request)
    {
        //得到第n页的数据
        PageInfo info = productInfoService.splitPage(page,PAGE_SIZE);
        request.setAttribute("info",info);
        vo = new ProductInfoVo();
        saveFileName = "";
        return "product";
    }

    //多条件显示数据

    @RequestMapping("/splitSelect")
    public String splitSelect(HttpServletRequest request)
    {
        //得到第n页的数据
        PageInfo info = productInfoService.selectConditionSplit(vo, PAGE_SIZE);
        request.setAttribute("info",info);
        request.setAttribute("vo",vo);
        saveFileName = "";
        return "product";
    }

    //多条件查询 分页在ajaxSplit中实现了

    @ResponseBody
    @RequestMapping(
            value = "/selectCondition",
            produces={"text/html;charset=UTF-8;","application/json;"}
    )
    public void selectCondition(ProductInfoVo vo,HttpSession session){
        //利用@ResponseBody只向session添加数据
        //刷新ProductInfoVo对象
        this.vo = vo;
        PageInfo info = productInfoService.selectConditionSplit(vo, PAGE_SIZE);
        session.setAttribute("info",info);
        saveFileName = "";
        System.out.println(vo);
        for(Object p : info.getList())
        {
            System.out.println(p);
        }
    }

    //Ajax分页翻页处理
    @ResponseBody
    @RequestMapping("/ajaxSplit")
    public void ajaxSpilt(int page, HttpSession session)
    {
        //利用@ResponseBody只向session添加数据
        //刷新ProductInfoVo对象
        vo.setPage(page);
        PageInfo info = productInfoService.selectConditionSplit(vo, PAGE_SIZE);
        session.setAttribute("info",info);
        saveFileName = "";
    }

    //异步ajax文件上传
    @ResponseBody
    @RequestMapping("/ajaxImg")
    public Object ajaxImg(MultipartFile pimage,HttpServletRequest request) throws IOException {
        //提取文件名 UUID + 上传图片后缀 .jpg .png
        saveFileName = FileNameUtil.getUUIDFileName() + FileNameUtil.getFileType(pimage.getOriginalFilename());
        //得到项目中图片存储路径
        String path = request.getServletContext().getRealPath("image_big");
        //将 文件pImage 以 文件名saveFileName 以路径 path 转存到项目中
        pimage.transferTo(new File(path + File.separator + saveFileName));
        //返回客户端json对象，封装图片的路径，为了在页面中实现回显
        JSONObject object = new JSONObject();
        object.put("imgurl",saveFileName);
        return object.toString();
    }

    @RequestMapping("/save")
    public String save(ProductInfo info, HttpServletRequest request){
        //图片处理好后，设置到商品对象中
        info.setpImage(saveFileName);
        info.setpDate(new Date());

        //到此为止，商品对象构建完毕，有自动从表单元素注入的，有上传图片的，有上架日期
        //完成数据库增加操作
        int num = -1;
        try {
            num = productInfoService.save(info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (num > 0)
        {
            request.getSession().setAttribute("msg", "增加成功!");
        }
        else
        {
            request.getSession().setAttribute("msg", "增加失败!");
        }
        //清空saveFileName，以便下次修改商品作为判断
        saveFileName="";
        //增删改后用重定向跳转
        return "redirect:/product/splitOne.action";
    }

    @RequestMapping("/one")
    public String one(int pId, int page, Model model){
        ProductInfo info = productInfoService.getById(pId);
        model.addAttribute("product",info);
        model.addAttribute("page",page);
        return "update";
    }

    @RequestMapping("/update")
    public String update(ProductInfo info, HttpServletRequest request){
        //因为Ajax的异步上传，如果有上传过，
        // 则saveFileName里有上传的图片名称，使用影藏表单提供上来的pImage名称
        // 否则saveFileName为空字符串
        if(saveFileName.length() != 0)
        {
            info.setpImage(saveFileName);
        }
        info.setpDate(new Date());
        int num = -1;
        try {
            num = productInfoService.update(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (num > 0)
        {
            request.getSession().setAttribute("msg", "更新成功!");
        }
        else
        {
            request.getSession().setAttribute("msg", "更新失败!");
        }
        //清空saveFileName，以便下次修改商品作为判断
        saveFileName="";
        return "forward:/product/splitSelect.action";
    }

    //单个删除

    @ResponseBody
    @RequestMapping(
            value = "/delete",
            produces={"text/html;charset=UTF-8;","application/json;"}
    )
    public String delete(int pId){
        int num = -1;
        try {
            num = productInfoService.delete(pId);
        } catch (Exception e) {
            e.printStackTrace();
            return "商品不可删除!";
        }
        if (num > 0)
        {
            return "删除成功!";
        }
        else
        {
            return "删除失败!";
        }
    }

    //批量删除

    @ResponseBody
    @RequestMapping(
            value = "/deleteBatch",
            produces={"text/html;charset=UTF-8;","application/json;"}
    )
    public String deleteBatch(String pIds){
        //pIds ="1,4,5"
        String[] pIdArray = pIds.split(",");
        int num = -1;
        try {
            num = productInfoService.deleteBatch(pIdArray);
        } catch (Exception e) {
            e.printStackTrace();
            return "商品不可删除!";
        }
        if (num > 0)
        {
            return "批量删除成功!";
        }
        else
        {
            return "批量删除失败!";
        }
    }
}
