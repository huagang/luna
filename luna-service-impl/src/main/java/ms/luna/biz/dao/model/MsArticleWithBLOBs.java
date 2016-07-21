package ms.luna.biz.dao.model;

import java.io.Serializable;

public class MsArticleWithBLOBs extends MsArticle implements Serializable {
    /**
     * Database Column Remarks:
     *   文章正文
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.content
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private String content;

    /**
     * Database Column Remarks:
     *   摘要
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column ms_article.abstract_content
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private String abstractContent;

    /**
     * This field was generated by Mark.
     *  ms_article
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.content
     *
     * @return the value of ms_article.content
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.content
     *
     * @param content the value for ms_article.content
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column ms_article.abstract_content
     *
     * @return the value of ms_article.abstract_content
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public String getAbstractContent() {
        return abstractContent;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column ms_article.abstract_content
     *
     * @param abstractContent the value for ms_article.abstract_content
     *
     * @mbggenerated Tue Jul 12 17:41:53 CST 2016
     */
    public void setAbstractContent(String abstractContent) {
        this.abstractContent = abstractContent == null ? null : abstractContent.trim();
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
        MsArticleWithBLOBs other = (MsArticleWithBLOBs) that;
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
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getAbstractContent() == null ? other.getAbstractContent() == null : this.getAbstractContent().equals(other.getAbstractContent()));
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
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getAbstractContent() == null) ? 0 : getAbstractContent().hashCode());
        return result;
    }
}