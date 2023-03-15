package ssm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssm.mapper.ProductTypeMapper;
import ssm.pojo.ProductType;
import ssm.pojo.ProductTypeExample;
import ssm.service.ProductTypeService;

import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {

    //业务逻辑层中的数据访问对象

    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAllProductType() {
        ProductTypeExample productTypeExample = new ProductTypeExample();
        List<ProductType> list = productTypeMapper.selectByExample(productTypeExample);
        return list;
    }
}
