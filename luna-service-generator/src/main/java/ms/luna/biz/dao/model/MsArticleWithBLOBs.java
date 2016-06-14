package ms.luna.biz.dao.model;

import java.io.Serializable;

public class MsArticleWithBLOBs extends MsArticle implements Serializable {
    private String content;

    private String abstract;

    private String abstractPic;

    private String audio;

    private String video;

    private static final long serialVersionUID = 1L;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAbstract() {
        return abstract;
    }

    public void setAbstract(String abstract) {
        this.abstract = abstract == null ? null : abstract.trim();
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
            && (this.getColumnId() == null ? other.getColumnId() == null : this.getColumnId().equals(other.getColumnId()))
            && (this.getUpHhmmss() == null ? other.getUpHhmmss() == null : this.getUpHhmmss().equals(other.getUpHhmmss()))
            && (this.getRegistHhmmss() == null ? other.getRegistHhmmss() == null : this.getRegistHhmmss().equals(other.getRegistHhmmss()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getAbstract() == null ? other.getAbstract() == null : this.getAbstract().equals(other.getAbstract()))
            && (this.getAbstractPic() == null ? other.getAbstractPic() == null : this.getAbstractPic().equals(other.getAbstractPic()))
            && (this.getAudio() == null ? other.getAudio() == null : this.getAudio().equals(other.getAudio()))
            && (this.getVideo() == null ? other.getVideo() == null : this.getVideo().equals(other.getVideo()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getColumnId() == null) ? 0 : getColumnId().hashCode());
        result = prime * result + ((getUpHhmmss() == null) ? 0 : getUpHhmmss().hashCode());
        result = prime * result + ((getRegistHhmmss() == null) ? 0 : getRegistHhmmss().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getAbstract() == null) ? 0 : getAbstract().hashCode());
        result = prime * result + ((getAbstractPic() == null) ? 0 : getAbstractPic().hashCode());
        result = prime * result + ((getAudio() == null) ? 0 : getAudio().hashCode());
        result = prime * result + ((getVideo() == null) ? 0 : getVideo().hashCode());
        return result;
    }
}