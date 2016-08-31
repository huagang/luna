package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class LunaTradeApplication implements Serializable {
    private Integer applicationId;

    private String contactName;

    private String contactPhone;

    private String email;

    private String idcardPicUrl;

    private String idcardPeriod;

    private String merchantName;

    private String merchantPhone;

    private String merchantNo;

    private String licencePicUrl;

    private String licencePeriod;

    private Integer accountType;

    private String accountName;

    private String accountBank;

    private String accountCity;

    private String accountAddress;

    private String accountNo;

    private Date updateTime;

    private Integer appStatus;

    private String merchantId;

    private static final long serialVersionUID = 1L;

    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName == null ? null : contactName.trim();
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone == null ? null : contactPhone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName == null ? null : merchantName.trim();
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone == null ? null : merchantPhone.trim();
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getLicencePicUrl() {
        return licencePicUrl;
    }

    public void setLicencePicUrl(String licencePicUrl) {
        this.licencePicUrl = licencePicUrl == null ? null : licencePicUrl.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
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
        LunaTradeApplication other = (LunaTradeApplication) that;
        return (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getContactName() == null ? other.getContactName() == null : this.getContactName().equals(other.getContactName()))
            && (this.getContactPhone() == null ? other.getContactPhone() == null : this.getContactPhone().equals(other.getContactPhone()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getIdcardPicUrl() == null ? other.getIdcardPicUrl() == null : this.getIdcardPicUrl().equals(other.getIdcardPicUrl()))
            && (this.getIdcardPeriod() == null ? other.getIdcardPeriod() == null : this.getIdcardPeriod().equals(other.getIdcardPeriod()))
            && (this.getMerchantName() == null ? other.getMerchantName() == null : this.getMerchantName().equals(other.getMerchantName()))
            && (this.getMerchantPhone() == null ? other.getMerchantPhone() == null : this.getMerchantPhone().equals(other.getMerchantPhone()))
            && (this.getMerchantNo() == null ? other.getMerchantNo() == null : this.getMerchantNo().equals(other.getMerchantNo()))
            && (this.getLicencePicUrl() == null ? other.getLicencePicUrl() == null : this.getLicencePicUrl().equals(other.getLicencePicUrl()))
            && (this.getLicencePeriod() == null ? other.getLicencePeriod() == null : this.getLicencePeriod().equals(other.getLicencePeriod()))
            && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
            && (this.getAccountName() == null ? other.getAccountName() == null : this.getAccountName().equals(other.getAccountName()))
            && (this.getAccountBank() == null ? other.getAccountBank() == null : this.getAccountBank().equals(other.getAccountBank()))
            && (this.getAccountCity() == null ? other.getAccountCity() == null : this.getAccountCity().equals(other.getAccountCity()))
            && (this.getAccountAddress() == null ? other.getAccountAddress() == null : this.getAccountAddress().equals(other.getAccountAddress()))
            && (this.getAccountNo() == null ? other.getAccountNo() == null : this.getAccountNo().equals(other.getAccountNo()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getAppStatus() == null ? other.getAppStatus() == null : this.getAppStatus().equals(other.getAppStatus()))
            && (this.getMerchantId() == null ? other.getMerchantId() == null : this.getMerchantId().equals(other.getMerchantId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getContactName() == null) ? 0 : getContactName().hashCode());
        result = prime * result + ((getContactPhone() == null) ? 0 : getContactPhone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getIdcardPicUrl() == null) ? 0 : getIdcardPicUrl().hashCode());
        result = prime * result + ((getIdcardPeriod() == null) ? 0 : getIdcardPeriod().hashCode());
        result = prime * result + ((getMerchantName() == null) ? 0 : getMerchantName().hashCode());
        result = prime * result + ((getMerchantPhone() == null) ? 0 : getMerchantPhone().hashCode());
        result = prime * result + ((getMerchantNo() == null) ? 0 : getMerchantNo().hashCode());
        result = prime * result + ((getLicencePicUrl() == null) ? 0 : getLicencePicUrl().hashCode());
        result = prime * result + ((getLicencePeriod() == null) ? 0 : getLicencePeriod().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
        result = prime * result + ((getAccountName() == null) ? 0 : getAccountName().hashCode());
        result = prime * result + ((getAccountBank() == null) ? 0 : getAccountBank().hashCode());
        result = prime * result + ((getAccountCity() == null) ? 0 : getAccountCity().hashCode());
        result = prime * result + ((getAccountAddress() == null) ? 0 : getAccountAddress().hashCode());
        result = prime * result + ((getAccountNo() == null) ? 0 : getAccountNo().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getAppStatus() == null) ? 0 : getAppStatus().hashCode());
        result = prime * result + ((getMerchantId() == null) ? 0 : getMerchantId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaTradeApplication [applicationId=" + applicationId + ",contactName=" + contactName + ",contactPhone=" + contactPhone + ",email=" + email + ",idcardPicUrl=" + idcardPicUrl + ",idcardPeriod=" + idcardPeriod + ",merchantName=" + merchantName + ",merchantPhone=" + merchantPhone + ",merchantNo=" + merchantNo + ",licencePicUrl=" + licencePicUrl + ",licencePeriod=" + licencePeriod + ",accountType=" + accountType + ",accountName=" + accountName + ",accountBank=" + accountBank + ",accountCity=" + accountCity + ",accountAddress=" + accountAddress + ",accountNo=" + accountNo + ",updateTime=" + updateTime + ",appStatus=" + appStatus + ",merchantId=" + merchantId + "]";
    }
}