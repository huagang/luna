package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class MsArticle implements Serializable {
    /**
     * Database Column Remarks:
     *   文章id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private Integer id;

    /**
     * Database Column Remarks:
     *   文章标题
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.title
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.short_title
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private String shortTitle;

    /**
     * Database Column Remarks:
     *   文章头图
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.abstract_pic
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private String abstractPic;

    /**
     * Database Column Remarks:
     *   音频文件地址
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.audio
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private String audio;

    /**
     * Database Column Remarks:
     *   视频文件地址
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.video
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private String video;

    /**
     * Database Column Remarks:
     *   业务id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.business_id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private Integer businessId;

    /**
     * Database Column Remarks:
     *   所属栏目
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.column_id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private Integer columnId;

    /**
     * Database Column Remarks:
     *   作者
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.author
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private String author;

    /**
     * Database Column Remarks:
     *   类型,0:中文,1:英文
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.type
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private Boolean type;

    /**
     * Database Column Remarks:
     *   对应的中/英文文章id
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.ref_id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private Integer refId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.up_hhmmss
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private Date upHhmmss;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.regist_hhmmss
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private Date registHhmmss;

    /**
     * Database Column Remarks:
     *   状态,0:未发布,1:已发布
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.status
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private Boolean status;

    /**
     * This field was generated by Mark.
     *  ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.id
     *
     * @return the value of ms_article.id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.id
     *
     * @param id the value for ms_article.id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.title
     *
     * @return the value of ms_article.title
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.title
     *
     * @param title the value for ms_article.title
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.short_title
     *
     * @return the value of ms_article.short_title
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public String getShortTitle() {
        return shortTitle;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.short_title
     *
     * @param shortTitle the value for ms_article.short_title
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle == null ? null : shortTitle.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.abstract_pic
     *
     * @return the value of ms_article.abstract_pic
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public String getAbstractPic() {
        return abstractPic;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.abstract_pic
     *
     * @param abstractPic the value for ms_article.abstract_pic
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setAbstractPic(String abstractPic) {
        this.abstractPic = abstractPic == null ? null : abstractPic.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.audio
     *
     * @return the value of ms_article.audio
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public String getAudio() {
        return audio;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.audio
     *
     * @param audio the value for ms_article.audio
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setAudio(String audio) {
        this.audio = audio == null ? null : audio.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.video
     *
     * @return the value of ms_article.video
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public String getVideo() {
        return video;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.video
     *
     * @param video the value for ms_article.video
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.business_id
     *
     * @return the value of ms_article.business_id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.business_id
     *
     * @param businessId the value for ms_article.business_id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.column_id
     *
     * @return the value of ms_article.column_id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Integer getColumnId() {
        return columnId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.column_id
     *
     * @param columnId the value for ms_article.column_id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.author
     *
     * @return the value of ms_article.author
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.author
     *
     * @param author the value for ms_article.author
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.type
     *
     * @return the value of ms_article.type
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Boolean getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.type
     *
     * @param type the value for ms_article.type
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setType(Boolean type) {
        this.type = type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.ref_id
     *
     * @return the value of ms_article.ref_id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Integer getRefId() {
        return refId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.ref_id
     *
     * @param refId the value for ms_article.ref_id
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setRefId(Integer refId) {
        this.refId = refId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.up_hhmmss
     *
     * @return the value of ms_article.up_hhmmss
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Date getUpHhmmss() {
        return upHhmmss;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.up_hhmmss
     *
     * @param upHhmmss the value for ms_article.up_hhmmss
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setUpHhmmss(Date upHhmmss) {
        this.upHhmmss = upHhmmss;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.regist_hhmmss
     *
     * @return the value of ms_article.regist_hhmmss
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Date getRegistHhmmss() {
        return registHhmmss;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.regist_hhmmss
     *
     * @param registHhmmss the value for ms_article.regist_hhmmss
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setRegistHhmmss(Date registHhmmss) {
        this.registHhmmss = registHhmmss;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.status
     *
     * @return the value of ms_article.status
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.status
     *
     * @param status the value for ms_article.status
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
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
        MsArticle other = (MsArticle) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getShortTitle() == null ? other.getShortTitle() == null : this.getShortTitle().equals(other.getShortTitle()))
            && (this.getAbstractPic() == null ? other.getAbstractPic() == null : this.getAbstractPic().equals(other.getAbstractPic()))
            && (this.getAudio() == null ? other.getAudio() == null : this.getAudio().equals(other.getAudio()))
            && (this.getVideo() == null ? other.getVideo() == null : this.getVideo().equals(other.getVideo()))
            && (this.getBusinessId() == null ? other.getBusinessId() == null : this.getBusinessId().equals(other.getBusinessId()))
            && (this.getColumnId() == null ? other.getColumnId() == null : this.getColumnId().equals(other.getColumnId()))
            && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getRefId() == null ? other.getRefId() == null : this.getRefId().equals(other.getRefId()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getShortTitle() == null) ? 0 : getShortTitle().hashCode());
        result = prime * result + ((getAbstractPic() == null) ? 0 : getAbstractPic().hashCode());
        result = prime * result + ((getAudio() == null) ? 0 : getAudio().hashCode());
        result = prime * result + ((getVideo() == null) ? 0 : getVideo().hashCode());
        result = prime * result + ((getBusinessId() == null) ? 0 : getBusinessId().hashCode());
        result = prime * result + ((getColumnId() == null) ? 0 : getColumnId().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getRefId() == null) ? 0 : getRefId().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsArticle [id=" + id + ",title=" + title + ",shortTitle=" + shortTitle + ",abstractPic=" + abstractPic + ",audio=" + audio + ",video=" + video + ",businessId=" + businessId + ",columnId=" + columnId + ",author=" + author + ",type=" + type + ",refId=" + refId + ",upHhmmss=" + upHhmmss + ",registHhmmss=" + registHhmmss + ",status=" + status + "]";
    }
}