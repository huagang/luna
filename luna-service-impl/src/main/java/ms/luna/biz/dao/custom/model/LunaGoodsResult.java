package ms.luna.biz.dao.custom.model;

import com.alibaba.fastjson.annotation.JSONField;
import ms.luna.biz.table.LunaGoodsTable;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created: by greek on 16/8/30.
 */
public class LunaGoodsResult {
    // id, merchant_id, business_id, pic, name, price, stock, sales, create_time, online_status
    @JSONField(name = LunaGoodsTable.FIELD_ID)
    Integer id;
    @JSONField(name = LunaGoodsTable.FIELD_NAME)
    String name;
    @JSONField(name = LunaGoodsTable.FIELD_MERCHANT_ID)
    String merchant_id;
    @JSONField(name = LunaGoodsTable.FIELD_BUSINESS_ID)
    Integer business_id;
    @JSONField(name = LunaGoodsTable.FIELD_PIC)
    String pic;
    @JSONField(name = LunaGoodsTable.FIELD_PRICE)
    BigDecimal price;
    @JSONField(name = LunaGoodsTable.FIELD_STOCK)
    Integer stock;
    @JSONField(name = LunaGoodsTable.FIELD_SALES)
    Integer sales;

    //@JSONField(name="regist_hhmmss", format="yyyy-MM-dd HH:mm:ss")
    @JSONField(name = LunaGoodsTable.FIELD_CREATE_TIME, format="yyyy-MM-dd HH:mm:ss")
    private Date create_time;

    @JSONField(name = LunaGoodsTable.FIELD_ONLINE_STATUS)
    String online_status;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public Integer getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(Integer business_id) {
        this.business_id = business_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getOnline_status() {
        return online_status;
    }

    public void setOnline_status(String online_status) {
        this.online_status = online_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }
}
