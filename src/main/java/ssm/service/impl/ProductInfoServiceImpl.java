package ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.mapper.ProductInfoMapper;
import ssm.pojo.ProductInfo;
import ssm.pojo.ProductInfoExample;
import ssm.pojo.vo.ProductInfoVo;
import ssm.service.ProductInfoService;

import java.util.List;

/**
 * @author xiao hao
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    //业务逻辑层中的数据访问对象

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        ProductInfoExample productInfoExample = new ProductInfoExample();
        List<ProductInfo> list = productInfoMapper.selectByExample(productInfoExample);
        return list;
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {

        //分页插件使用PageHelper
        PageHelper.startPage(pageNum, pageSize);

        //进行PageInfo的数据封装
        //进行有条件的查询操作，必须要创建ProductInfoExample对象
        ProductInfoExample productInfoExample = new ProductInfoExample();

        //设置排序，按主键降序排序
        //select * from product_info order by p_id desc

        productInfoExample.setOrderByClause("p_id desc");

        //设置完排序后，取集合， 一定要在取集合之前进行PageHelper的设置PageHelper.startPage(pageNum, pageSize)

        List<ProductInfo> list = productInfoMapper.selectByExample(productInfoExample);

        //将查询到的集合封装在PageInfo对象中

        return new PageInfo<ProductInfo>(list);
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pId) {
        return productInfoMapper.selectByPrimaryKey(pId);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKeySelective(info);
    }

    @Override
    public int delete(int pId) {
        return productInfoMapper.deleteByPrimaryKey(pId);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo selectConditionSplit(ProductInfoVo vo, int pageSize) {

        //分页插件使用PageHelper
        PageHelper.startPage(vo.getPage(), pageSize);

        List<ProductInfo> list = productInfoMapper.selectCondition(vo);

        return new PageInfo<ProductInfo>(list);
    }


}
