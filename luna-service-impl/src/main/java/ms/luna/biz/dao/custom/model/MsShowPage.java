package ms.luna.biz.dao.custom.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;

import ms.luna.biz.dao.custom.MsShowPageDAO;

@JSONType(ignores={})
public class MsShowPage {
	
	@JSONField(name="page_id")
    private String pageId;
    @JSONField(name=MsShowPageDAO.FIELD_PAGE_NAME)
    private String pageName;
    @JSONField(name=MsShowPageDAO.FIELD_PAGE_CODE)
    private String pageCode;
    @JSONField(name=MsShowPageDAO.FIELD_PAGE_ORDER)
    private int pageOrder;
    @JSONField(name=MsShowPageDAO.FIELD_PAGE_CONTENT)
    private Map<String, Object> pageContent;
    @JSONField(name=MsShowPageDAO.FIELD_PAGE_ADDR)
    private String pageAddr;
    @JSONField(name=MsShowPageDAO.FIELD_APP_ID)
    private int appId;
    @JSONField(name=MsShowPageDAO.FIELD_SHARE_TITLE)
    private String shareTitle;
    @JSONField(name=MsShowPageDAO.FIELD_SHARE_DESC)
    private String shareDesc;
    @JSONField(name=MsShowPageDAO.FIELD_SHARE_PIC)
    private String sharePic;
    @JSONField(name=MsShowPageDAO.FIELD_CREATE_TIME)
    private Timestamp createTime;
    @JSONField(name=MsShowPageDAO.FIELD_UPDATE_TIME)
    private Timestamp updateTime;
    @JSONField(name=MsShowPageDAO.FIELD_UPDATE_USER)
    private String updateUser;
    @JSONField(name=MsShowPageDAO.FIELD_PAGE_TYPE)
    private String pageType;
    @JSONField(name=MsShowPageDAO.FIELD_PAGE_HEIGHT)
    private String pageHeight;
    @JSONField(name=MsShowPageDAO.FIELD_PAGE_TIME)
    private Double pageTime;
    @JSONField(name = MsShowPageDAO.FIELD_SHARE_INFO)
    private Map<String, Object> shareInfo;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageCode() {
        return pageCode;
    }
    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public int getPageOrder() {
        return pageOrder;
    }
    public void setPageOrder(int pageOrder) {
        this.pageOrder = pageOrder;
    }

    public Map<String, Object> getPageContent() {
        return pageContent;
    }
    public void setPageContent(Map<String, Object> pageContent) {
        this.pageContent = pageContent;
    }

    public String getPageAddr() {
        return pageAddr;
    }
    public void setPageAddr(String pageAddr) {
        this.pageAddr = pageAddr;
    }

    public int getAppId() {
        return appId;
    }
    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getShareTitle() {
        return shareTitle;
    }
    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareDesc() {
        return shareDesc;
    }
    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public String getSharePic() {
        return sharePic;
    }
    public void setSharePic(String sharePic) {
        this.sharePic = sharePic;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public String getPageType() {
        return pageType;
    }
    public void setPageType(String pageType) {
        this.pageType = pageType;
    }

    public String getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(String pageHeight) {
        this.pageHeight = pageHeight;
    }

    public Double getPageTime() {
        return pageTime;
    }

    public void setPageTime(Double pageTime) {
        this.pageTime = pageTime;
    }

    public Map<String, Object> getShareInfo() {
        return shareInfo;
    }

    public void setShareInfo(Map<String, Object> shareInfo) {
        this.shareInfo = shareInfo;
    }

    public MsShowPage() {
    }

}
