package ms.luna.biz.dao.model;

import com.alibaba.fastjson.annotation.JSONField;
import ms.luna.biz.table.MsCRMTable;
import ms.luna.biz.table.MsMerchantManageTable;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MsMerchantManage implements Serializable {
    @JSONField(name = MsCRMTable.FIELD_MERCHANT_ID)
    private String merchantId;
    @JSONField(name = MsCRMTable.FIELD_MERCHANT_NM)
    private String merchantNm;
    @JSONField(name = MsCRMTable.FIELD_MERCHANT_PHONENUM)
    private String merchantPhonenum;
    @JSONField(name = MsCRMTable.FIELD_CATEGORY_ID)
    private String categoryId;
    @JSONField(name = MsCRMTable.FIELD_PROVINCE_ID)
    private String provinceId;
    @JSONField(name = MsCRMTable.FIELD_CITY_ID)
    private String cityId;
    @JSONField(name = MsCRMTable.FIELD_COUNTY_ID)
    private String countyId;
    @JSONField(name = MsCRMTable.FIELD_MERCHANT_ADDR)
    private String merchantAddr;
    @JSONField(name = MsCRMTable.FIELD_RESOURCE_CONTENT)
    private String resourceContent;
    @JSONField(name = MsCRMTable.FIELD_LAT)
    private BigDecimal lat;
    @JSONField(name = MsCRMTable.FIELD_LNG)
    private BigDecimal lng;
    @JSONField(name = MsCRMTable.FIELD_MERCHANT_INFO)
    private String merchantInfo;
    @JSONField(name = MsCRMTable.FIELD_CONTACT_NM)
    private String contactNm;
    @JSONField(name = MsCRMTable.FIELD_CONTACT_PHONENUM)
    private String contactPhonenum;
    @JSONField(name = MsCRMTable.FIELD_CONTACT_MAIL)
    private String contactMail;
    @JSONField(name = MsCRMTable.FIELD_SALESMAN_ID)
    private String salesmanId;
    @JSONField(name = MsCRMTable.FIELD_SALESMAN_NM)
    private String salesmanNm;
    @JSONField(name = MsCRMTable.FIELD_STATUS_ID)
    private String statusId;
    @JSONField(name = MsCRMTable.FIELD_DEL_FLG)
    private String delFlg;
    @JSONField(name = MsCRMTable.FIELD_REGIST_HHMMSS)
    private Date registHhmmss;
    @JSONField(name = MsCRMTable.FIELD_UP_HHMMSS)
    private Date upHhmmss;
    @JSONField(name = MsCRMTable.FIELD_UPDATED_BY_UNIQUE_ID)
    private String updatedByUniqueId;
    @JSONField(name = MsMerchantManageTable.FIELD_TRADE_STATUS)
    private Integer tradeStatus;

    private Integer applicationId;

    private String idcardPicUrl;

    private String idcardPeriod;

    private String merchantNo;

    private String licencePeriod;

    private Integer accountType;

    private String accountName;

    private String accountBank;

    private String accountCity;

    private String accountAddress;

    private String accountNo;

    private Integer appStatus;

    private String accountProvince;

    private static final long serialVersionUID = 1L;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    public String getMerchantNm() {
        return merchantNm;
    }

    public void setMerchantNm(String merchantNm) {
        this.merchantNm = merchantNm == null ? null : merchantNm.trim();
    }

    public String getMerchantPhonenum() {
        return merchantPhonenum;
    }

    public void setMerchantPhonenum(String merchantPhonenum) {
        this.merchantPhonenum = merchantPhonenum == null ? null : merchantPhonenum.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId == null ? null : countyId.trim();
    }

    public String getMerchantAddr() {
        return merchantAddr;
    }

    public void setMerchantAddr(String merchantAddr) {
        this.merchantAddr = merchantAddr == null ? null : merchantAddr.trim();
    }

    public String getResourceContent() {
        return resourceContent;
    }

    public void setResourceContent(String resourceContent) {
        this.resourceContent = resourceContent == null ? null : resourceContent.trim();
    }

    public BigDecimal getLat() {
        return lat;
    }

    public void setLat(BigDecimal lat) {
        this.lat = lat;
    }

    public BigDecimal getLng() {
        return lng;
    }

    public void setLng(BigDecimal lng) {
        this.lng = lng;
    }

    public String getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(String merchantInfo) {
        this.merchantInfo = merchantInfo == null ? null : merchantInfo.trim();
    }

    public String getContactNm() {
        return contactNm;
    }

    public void setContactNm(String contactNm) {
        this.contactNm = contactNm == null ? null : contactNm.trim();
    }

    public String getContactPhonenum() {
        return contactPhonenum;
    }

    public void setContactPhonenum(String contactPhonenum) {
        this.contactPhonenum = contactPhonenum == null ? null : contactPhonenum.trim();
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail == null ? null : contactMail.trim();
    }

    public String getSalesmanId() {
        return salesmanId;
    }

    public void setSalesmanId(String salesmanId) {
        this.salesmanId = salesmanId == null ? null : salesmanId.trim();
    }

    public String getSalesmanNm() {
        return salesmanNm;
    }

    public void setSalesmanNm(String salesmanNm) {
        this.salesmanNm = salesmanNm == null ? null : salesmanNm.trim();
    }

    public String getStatusId() {
        return statusId;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId == null ? null : statusId.trim();
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg == null ? null : delFlg.trim();
    }

    public Date getRegistHhmmss() {
        return registHhmmss;
    }

    public void setRegistHhmmss(Date registHhmmss) {
        this.registHhmmss = registHhmmss;
    }

    public Date getUpHhmmss() {
        return upHhmmss;
    }

    public void setUpHhmmss(Date upHhmmss) {
        this.upHhmmss = upHhmmss;
    }

    public String getUpdatedByUniqueId() {
        return updatedByUniqueId;
    }

    public void setUpdatedByUniqueId(String updatedByUniqueId) {
        this.updatedByUniqueId = updatedByUniqueId == null ? null : updatedByUniqueId.trim();
    }

    public Integer getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(Integer tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getIdcardPicUrl() {
        return idcardPicUrl;
    }

    public void setIdcardPicUrl(String idcardPicUrl) {
        this.idcardPicUrl = idcardPicUrl == null ? null : idcardPicUrl.trim();
    }

    public String getIdcardPeriod() {
        return idcardPeriod;
    }

    public void setIdcardPeriod(String idcardPeriod) {
        this.idcardPeriod = idcardPeriod == null ? null : idcardPeriod.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getLicencePeriod() {
        return licencePeriod;
    }

    public void setLicencePeriod(String licencePeriod) {
        this.licencePeriod = licencePeriod == null ? null : licencePeriod.trim();
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank == null ? null : accountBank.trim();
    }

    public String getAccountCity() {
        return accountCity;
    }

    public void setAccountCity(String accountCity) {
        this.accountCity = accountCity == null ? null : accountCity.trim();
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress == null ? null : accountAddress.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }

    public String getAccountProvince() {
        return accountProvince;
    }

    public void setAccountProvince(String accountProvince) {
        this.accountProvince = accountProvince == null ? null : accountProvince.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MsMerchantManage other = (MsMerchantManage) that;
        return (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()))
            && (this.getMerchantNm() == null ? other.getMerchantNm() == null : this.getMerchantNm().equals(other.getMerchantNm()))
            && (this.getMerchantPhonenum() == null ? other.getMerchantPhonenum() == null : this.getMerchantPhonenum().equals(other.getMerchantPhonenum()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getProvinceId() == null ? other.getProvinceId() == null : this.getProvinceId().equals(other.getProvinceId()))
            && (this.getCityId() == null ? other.getCityId() == null : this.getCityId().equals(other.getCityId()))
            && (this.getCountyId() == null ? other.getCountyId() == null : this.getCountyId().equals(other.getCountyId()))
            && (this.getMerchantAddr() == null ? other.getMerchantAddr() == null : this.getMerchantAddr().equals(other.getMerchantAddr()))
            && (this.getResourceContent() == null ? other.getResourceContent() == null : this.getResourceContent().equals(other.getResourceContent()))
            && (this.getLat() == null ? other.getLat() == null : this.getLat().equals(other.getLat()))
            && (this.getLng() == null ? other.getLng() == null : this.getLng().equals(other.getLng()))
            && (this.getMerchantInfo() == null ? other.getMerchantInfo() == null : this.getMerchantInfo().equals(other.getMerchantInfo()))
            && (this.getContactNm() == null ? other.getContactNm() == null : this.getContactNm().equals(other.getContactNm()))
            && (this.getContactPhonenum() == null ? other.getContactPhonenum() == null : this.getContactPhonenum().equals(other.getContactPhonenum()))
            && (this.getContactMail() == null ? other.getContactMail() == null : this.getContactMail().equals(other.getContactMail()))
            && (this.getSalesmanId() == null ? other.getSalesmanId() == null : this.getSalesmanId().equals(other.getSalesmanId()))
            && (this.getSalesmanNm() == null ? other.getSalesmanNm() == null : this.getSalesmanNm().equals(other.getSalesmanNm()))
            && (this.getStatusId() == null ? other.getStatusId() == null : this.getStatusId().equals(other.getStatusId()))
            && (this.getDelFlg() == null ? other.getDelFlg() == null : this.getDelFlg().equals(other.getDelFlg()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getUpdatedByUniqueId() == null ? other.getUpdatedByUniqueId() == null : this.getUpdatedByUniqueId().equals(other.getUpdatedByUniqueId()))
            && (this.getTradeStatus() == null ? other.getTradeStatus() == null : this.getTradeStatus().equals(other.getTradeStatus()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getIdcardPicUrl() == null ? other.getIdcardPicUrl() == null : this.getIdcardPicUrl().equals(other.getIdcardPicUrl()))
            && (this.getIdcardPeriod() == null ? other.getIdcardPeriod() == null : this.getIdcardPeriod().equals(other.getIdcardPeriod()))
            && (this.getMerchantNo() == null ? other.getMerchantNo() == null : this.getMerchantNo().equals(other.getMerchantNo()))
            && (this.getLicencePeriod() == null ? other.getLicencePeriod() == null : this.getLicencePeriod().equals(other.getLicencePeriod()))
            && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
            && (this.getAccountName() == null ? other.getAccountName() == null : this.getAccountName().equals(other.getAccountName()))
            && (this.getAccountBank() == null ? other.getAccountBank() == null : this.getAccountBank().equals(other.getAccountBank()))
            && (this.getAccountCity() == null ? other.getAccountCity() == null : this.getAccountCity().equals(other.getAccountCity()))
            && (this.getAccountAddress() == null ? other.getAccountAddress() == null : this.getAccountAddress().equals(other.getAccountAddress()))
            && (this.getAccountNo() == null ? other.getAccountNo() == null : this.getAccountNo().equals(other.getAccountNo()))
            && (this.getAppStatus() == null ? other.getAppStatus() == null : this.getAppStatus().equals(other.getAppStatus()))
            && (this.getAccountProvince() == null ? other.getAccountProvince() == null : this.getAccountProvince().equals(other.getAccountProvince()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        result = prime * result + ((getMerchantNm() == null) ? 0 : getMerchantNm().hashCode());
        result = prime * result + ((getMerchantPhonenum() == null) ? 0 : getMerchantPhonenum().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getProvinceId() == null) ? 0 : getProvinceId().hashCode());
        result = prime * result + ((getCityId() == null) ? 0 : getCityId().hashCode());
        result = prime * result + ((getCountyId() == null) ? 0 : getCountyId().hashCode());
        result = prime * result + ((getMerchantAddr() == null) ? 0 : getMerchantAddr().hashCode());
        result = prime * result + ((getResourceContent() == null) ? 0 : getResourceContent().hashCode());
        result = prime * result + ((getLat() == null) ? 0 : getLat().hashCode());
        result = prime * result + ((getLng() == null) ? 0 : getLng().hashCode());
        result = prime * result + ((getMerchantInfo() == null) ? 0 : getMerchantInfo().hashCode());
        result = prime * result + ((getContactNm() == null) ? 0 : getContactNm().hashCode());
        result = prime * result + ((getContactPhonenum() == null) ? 0 : getContactPhonenum().hashCode());
        result = prime * result + ((getContactMail() == null) ? 0 : getContactMail().hashCode());
        result = prime * result + ((getSalesmanId() == null) ? 0 : getSalesmanId().hashCode());
        result = prime * result + ((getSalesmanNm() == null) ? 0 : getSalesmanNm().hashCode());
        result = prime * result + ((getStatusId() == null) ? 0 : getStatusId().hashCode());
        result = prime * result + ((getDelFlg() == null) ? 0 : getDelFlg().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getUpdatedByUniqueId() == null) ? 0 : getUpdatedByUniqueId().hashCode());
        result = prime * result + ((getTradeStatus() == null) ? 0 : getTradeStatus().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getIdcardPicUrl() == null) ? 0 : getIdcardPicUrl().hashCode());
        result = prime * result + ((getIdcardPeriod() == null) ? 0 : getIdcardPeriod().hashCode());
        result = prime * result + ((getMerchantNo() == null) ? 0 : getMerchantNo().hashCode());
        result = prime * result + ((getLicencePeriod() == null) ? 0 : getLicencePeriod().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
        result = prime * result + ((getAccountName() == null) ? 0 : getAccountName().hashCode());
        result = prime * result + ((getAccountBank() == null) ? 0 : getAccountBank().hashCode());
        result = prime * result + ((getAccountCity() == null) ? 0 : getAccountCity().hashCode());
        result = prime * result + ((getAccountAddress() == null) ? 0 : getAccountAddress().hashCode());
        result = prime * result + ((getAccountNo() == null) ? 0 : getAccountNo().hashCode());
        result = prime * result + ((getAppStatus() == null) ? 0 : getAppStatus().hashCode());
        result = prime * result + ((getAccountProvince() == null) ? 0 : getAccountProvince().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsMerchantManage [merchantId=" + merchantId + ",merchantNm=" + merchantNm + ",merchantPhonenum=" + merchantPhonenum + ",categoryId=" + categoryId + ",provinceId=" + provinceId + ",cityId=" + cityId + ",countyId=" + countyId + ",merchantAddr=" + merchantAddr + ",resourceContent=" + resourceContent + ",lat=" + lat + ",lng=" + lng + ",merchantInfo=" + merchantInfo + ",contactNm=" + contactNm + ",contactPhonenum=" + contactPhonenum + ",contactMail=" + contactMail + ",salesmanId=" + salesmanId + ",salesmanNm=" + salesmanNm + ",statusId=" + statusId + ",delFlg=" + delFlg + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",updatedByUniqueId=" + updatedByUniqueId + ",tradeStatus=" + tradeStatus + ",applicationId=" + applicationId + ",idcardPicUrl=" + idcardPicUrl + ",idcardPeriod=" + idcardPeriod + ",merchantNo=" + merchantNo + ",licencePeriod=" + licencePeriod + ",accountType=" + accountType + ",accountName=" + accountName + ",accountBank=" + accountBank + ",accountCity=" + accountCity + ",accountAddress=" + accountAddress + ",accountNo=" + accountNo + ",appStatus=" + appStatus + ",accountProvince=" + accountProvince + "]";
    }
}