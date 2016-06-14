package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;
  
public class MsArticle implements Serializable {
    private Integer id;

    private String title;

    private String abstractPic;

    private String audio;

    private String video;

    private Integer columnId;

    private Date upHhmmss;

    private Date registHhmmss;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAbstractPic() {
        return abstractPic;
    }

    public void setAbstractPic(String abstractPic) {
        this.abstractPic = abstractPic == null ? null : abstractPic.trim();
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio == null ? null : audio.trim();
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video == null ? null : video.trim();
    }

    public Integer getColumnId() {
        return columnId;
    }

    public void setColumnId(Integer columnId) {
        this.columnId = columnId;
    }

    public Date getUpHhmmss() {
        return upHhmmss;
    }

    public void setUpHhmmss(Date upHhmmss) {
        this.upHhmmss = upHhmmss;
    }

    public Date getRegistHhmmss() {
        return registHhmmss;
    }

    public void setRegistHhmmss(Date registHhmmss) {
        this.registHhmmss = registHhmmss;
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
        MsArticle other = (MsArticle) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getAbstractPic() == null ? other.getAbstractPic() == null : this.getAbstractPic().equals(other.getAbstractPic()))
            && (this.getAudio() == null ? other.getAudio() == null : this.getAudio().equals(other.getAudio()))
            && (this.getVideo() == null ? other.getVideo() == null : this.getVideo().equals(other.getVideo()))
            && (this.getColumnId() == null ? other.getColumnId() == null : this.getColumnId().equals(other.getColumnId()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getAbstractPic() == null) ? 0 : getAbstractPic().hashCode());
        result = prime * result + ((getAudio() == null) ? 0 : getAudio().hashCode());
        result = prime * result + ((getVideo() == null) ? 0 : getVideo().hashCode());
        result = prime * result + ((getColumnId() == null) ? 0 : getColumnId().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "MsArticle [id=" + id + ",title=" + title + ",abstractPic=" + abstractPic + ",audio=" + audio + ",video=" + video + ",columnId=" + columnId + ",upHhmmss=" + upHhmmss + ",registHhmmss=" + registHhmmss + "]";
    }
}