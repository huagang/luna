package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsRFunctionResourceUri implements Serializable {
    private Integer id;

    private String msFunctionCode;

    private Integer resourceId;

    private String delFlg;

    private Date registHhmmss;

    private Date upHhmmss;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsFunctionCode() {
        return msFunctionCode;
    }

    public void setMsFunctionCode(String msFunctionCode) {
        this.msFunctionCode = msFunctionCode == null ? null : msFunctionCode.trim();
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
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
        MsRFunctionResourceUri other = (MsRFunctionResourceUri) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMsFunctionCode() == null ? other.getMsFunctionCode() == null : this.getMsFunctionCode().equals(other.getMsFunctionCode()))
            && (this.getResourceId() == null ? other.getResourceId() == null : this.getResourceId().equals(other.getResourceId()))
            && (this.getDelFlg() == null ? other.getDelFlg() == null : this.getDelFlg().equals(other.getDelFlg()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMsFunctionCode() == null) ? 0 : getMsFunctionCode().hashCode());
        result = prime * result + ((getResourceId() == null) ? 0 : getResourceId().hashCode());
        result = prime * result + ((getDelFlg() == null) ? 0 : getDelFlg().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsRFunctionResourceUri [id=" + id + ",msFunctionCode=" + msFunctionCode + ",resourceId=" + resourceId + ",delFlg=" + delFlg + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + "]";
    }
}