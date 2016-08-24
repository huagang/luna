package ms.luna.biz.dao.custom.model;

import java.util.List;

public class MsArticleParameter extends BasicModel {

	private int columnId;
	private List<Integer> businessIds = null;

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public List<Integer> getBusinessIds() {
		return businessIds;
	}

	public void setBusinessIds(List<Integer> businessIds) {
		this.businessIds = businessIds;
	}
}
