package ms.luna.biz.dao.custom.model;
/** 
  * @author greek
  */
public class MsRouteResult {

	private Integer id;
	
	private String name;
	
	private Integer business_id;
	
	private String business_name;
	
	private Integer cost_id;
	
	private String luna_name;
	
	private String description;
	
	private String cover;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBusiness_id() {
		return business_id;
	}

	public void setBusiness_id(Integer business_id) {
		this.business_id = business_id;
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public Integer getCost_id() {
		return cost_id;
	}

	public void setCost_id(Integer cost_id) {
		this.cost_id = cost_id;
	}

	public String getLuna_name() {
		return luna_name;
	}

	public void setLuna_name(String luna_name) {
		this.luna_name = luna_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}
	
}
