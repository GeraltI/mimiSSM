package ssm.pojo.vo;

public class ProductInfoVo {

    //商品名称

    private String pName;

    //商品类型

    private Integer typeId;

    //商品最低价格

    private Integer minPrice;

    //商品最高价格

    private Integer maxPrice;

    //页码

    private Integer page = 1;

    public ProductInfoVo() {
    }

    public ProductInfoVo(String pName, Integer typeId, Integer minPrice, Integer maxPrice) {
        this.pName = pName;
        this.typeId = typeId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    public ProductInfoVo(String pName, Integer typeId, Integer minPrice, Integer maxPrice, Integer page) {
        this.pName = pName;
        this.typeId = typeId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.page = page;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ProductInfoVo{" +
                "pName='" + pName + '\'' +
                ", typeId=" + typeId +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}

