package ms.luna.biz.dao.custom.model;

import java.util.List;

/**
 * Created: by greek on 16/8/30.
 */
public class LunaGoodsParameter extends BasicModel {

    private Integer businessId;

    private Byte online_status;

    private List<Integer> ids;

    public Byte getOnline_status() {
        return online_status;
    }

    public void setOnline_status(Byte online_status) {
        this.online_status = online_status;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }
}
