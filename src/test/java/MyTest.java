import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ssm.mapper.ProductInfoMapper;
import ssm.pojo.ProductInfo;
import ssm.pojo.vo.ProductInfoVo;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Test
    public void testSelectCondition()
    {
        ProductInfoVo vo = new ProductInfoVo();
        List<ProductInfo> productInfoList;
        productInfoList = productInfoMapper.selectCondition(vo);
        for(ProductInfo productInfo : productInfoList)
        {
            System.out.println(productInfo);
        }
        System.out.println("***************************************************");
        //模糊查询商品名称中带4的商品
        vo.setpName("4");
        productInfoList = productInfoMapper.selectCondition(vo);
        for(ProductInfo productInfo : productInfoList)
        {
            System.out.println(productInfo);
        }
        System.out.println("***************************************************");
        vo.setpName("");
        //查询商品类别为2的商品
        vo.setTypeId(2);
        productInfoList = productInfoMapper.selectCondition(vo);
        for(ProductInfo productInfo : productInfoList)
        {
            System.out.println(productInfo);
        }
        System.out.println("***************************************************");
        vo.setTypeId(null);
        //查询价格大于等于500的商品
        vo.setMinPrice(500);
        productInfoList = productInfoMapper.selectCondition(vo);
        for(ProductInfo productInfo : productInfoList)
        {
            System.out.println(productInfo);
        }
        System.out.println("***************************************************");
        vo.setMinPrice(null);
        //查询价格小于等于1000的商品
        vo.setMaxPrice(1000);
        productInfoList = productInfoMapper.selectCondition(vo);
        for(ProductInfo productInfo : productInfoList)
        {
            System.out.println(productInfo);
        }
        System.out.println("***************************************************");
        vo.setpName("4");
        vo.setTypeId(1);
        vo.setMinPrice(500);
        //查询价格大于等于500小于等于1000的商品
        productInfoList = productInfoMapper.selectCondition(vo);
        for(ProductInfo productInfo : productInfoList)
        {
            System.out.println(productInfo);
        }
    }

}
