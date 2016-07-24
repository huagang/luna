package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsCategory implements Serializable {
    private String categoryId;

    private String nmZh;

    private String nmEn;

    private String delFlg;

    private Date registHhmmss;

    private Date upHhmmss;

    private String updatedByWjnm;

    private static final long serialVersionUID = 1L;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId == null ? null : categoryId.trim();
    }

    public String getNmZh() {
        return nmZh;
    }

    public void setNmZh(String nmZh) {
        this.nmZh = nmZh == null ? null : nmZh.trim();
    }

    public String getNmEn() {
        return nmEn;
    }

    public void setNmEn(String nmEn) {
        this.nmEn = nmEn == null ? null : nmEn.trim();
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

    public String getUpdatedByWjnm() {
        return updatedByWjnm;
    }

    public void setUpdatedByWjnm(String updatedByWjnm) {
        this.updatedByWjnm = updatedByWjnm == null ? null : updatedByWjnm.trim();
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
        MsCategory other = (MsCategory) that;
        return (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getNmZh() == null ? other.getNmZh() == null : this.getNmZh().equals(other.getNmZh()))
            && (this.getNmEn() == null ? other.getNmEn() == null : this.getNmEn().equals(other.getNmEn()))
            && (this.getDelFlg() == null ? other.getDelFlg() == null : this.getDelFlg().equals(other.getDelFlg()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getUpdatedByWjnm() == null ? other.getUpdatedByWjnm() == null : this.getUpdatedByWjnm().equals(other.getUpdatedByWjnm()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getNmZh() == null) ? 0 : getNmZh().hashCode());
        result = prime * result + ((getNmEn() == null) ? 0 : getNmEn().hashCode());
        result = prime * result + ((getDelFlg() == null) ? 0 : getDelFlg().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getUpdatedByWjnm() == null) ? 0 : getUpdatedByWjnm().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsCategory [categoryId=" + categoryId + ",nmZh=" + nmZh + ",nmEn=" + nmEn + ",delFlg=" + delFlg + ",registHhmmss=" + registHhmmss + ",upHhmmss=" + upHhmmss + ",updatedByWjnm=" + updatedByWjnm + "]";
    }
}