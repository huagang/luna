package ms.luna.biz.dao.custom;

import java.util.List;
import java.util.Map;

import ms.luna.biz.dao.custom.model.MsShowPage;

public interface MsShowPageDAO {
	
	public static final String COLLECTION_NAME = "show_page";
	public static final String FIELD_PAGE_ID = "_id";
	public static final String FIELD_PAGE_NAME = "page_name";
	public static final String FIELD_PAGE_CODE = "page_code";
	public static final String FIELD_PAGE_ORDER = "page_order";
	public static final String FIELD_PAGE_CONTENT = "page_content";
	public static final String FIELD_PAGE_ADDR = "page_addr";
//	public static final String FIELD_PAGE_STATUS = "page_status";
	public static final String FIELD_APP_ID = "app_id";
	public static final String FIELD_SHARE_TITLE = "share_title";
	public static final String FIELD_SHARE_DESC = "share_desc";
	public static final String FIELD_SHARE_PIC = "share_pic";
	public static final String FIELD_CREATE_TIME = "create_time";
	public static final String FIELD_UPDATE_TIME = "update_time";
	public static final String FIELD_UPDATE_USER = "update_user";
	public static final String FIELD_PAGE_TYPE = "page_type";
	public static final String FIELD_PAGE_HEIGHT = "page_height";
	public static final String FIELD_PAGE_TIME = "page_time";
	public static final String FIELD_SHARE_INFO = "share_info";
	public static final String FIELD_SHARE_LINK = "share_link";

	public List<MsShowPage>	readAllPageDetailByAppId(int appId);
	public List<MsShowPage> readIndexPageDetailByAppId(int appId);
	public List<MsShowPage> readAllPageSummaryByAppId(int appId, List<String> summaryFields);
	public MsShowPage readPageSummary(String pageId, List<String> summaryFields);
	public MsShowPage readPageDetail(String pageId);
	
	/**
	 * 
	 * @param page
	 * @return pageId mongodb中的document key(_id)
	 */
	public String createOnePage(MsShowPage page);
	
	public void updatePageName(MsShowPage page);
		
	public void updatePage(MsShowPage page);
	
	public void updatePages(List<MsShowPage> pages);
	
	public void deletePagesByAppId(int appId);
	public void deletePageById(String pageId);

	public void copyAllPages(int sourceAppId, int destAppId, String user);
	
	public void updatePageOrder(Map<String, Integer> pageOrders);

}