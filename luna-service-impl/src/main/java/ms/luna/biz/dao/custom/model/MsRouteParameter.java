package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

/** 
  * @author greek
  */
public class MsRouteParameter implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer limit;
	
	private Integer offset;
	
	private boolean range;

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public boolean getRange() {
		return range;
	}

	public void setRange(boolean range) {
		this.range = range;
	}
	
}
