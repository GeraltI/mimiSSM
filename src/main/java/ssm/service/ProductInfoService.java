package ssm.service;

import com.github.pagehelper.PageInfo;
import ssm.pojo.ProductInfo;
import ssm.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {

    //显示所有商品(不分页)

    List<ProductInfo> getAll();

    //显示分页商品

    PageInfo splitPage(int pageNum, int pageSize);

    //保存商品

    int save(ProductInfo info);

    //根据主键id查找商品

    ProductInfo getById(int pId);

    //修改商品

    int update(ProductInfo info);

    //删除单个商品

    int delete(int pId);

    //批量删除

    int deleteBatch(String[] ids);

    //多条件查询

    List<ProductInfo> selectCondition(ProductInfoVo vo);

    //多条件查询分页

    public PageInfo selectConditionSplit(ProductInfoVo vo,int pageSize);
}
