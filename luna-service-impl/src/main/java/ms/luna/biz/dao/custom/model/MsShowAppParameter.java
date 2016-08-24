package ms.luna.biz.dao.custom.model;

import java.util.List;

public class MsShowAppParameter extends BasicModel {

    private List<Integer> businessIds = null;

    public List<Integer> getBusinessIds() {
        return businessIds;
    }

    public void setBusinessIds(List<Integer> businessIds) {
        this.businessIds = businessIds;
    }
}
