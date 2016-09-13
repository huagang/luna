package ms.luna.biz.dao.model;

import java.io.Serializable;
import java.util.Date;

public class RoomOtherInfoKey implements Serializable {
    private Integer roomId;

    private Date day;

    private static final long serialVersionUID = 1L;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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
        RoomOtherInfoKey other = (RoomOtherInfoKey) that;
        return (this.getRoomId() == null ? other.getRoomId() == null : this.getRoomId().equals(other.getRoomId()))
            && (this.getDay() == null ? other.getDay() == null : this.getDay().equals(other.getDay()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoomId() == null) ? 0 : getRoomId().hashCode());
        result = prime * result + ((getDay() == null) ? 0 : getDay().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "RoomOtherInfoKey [roomId=" + roomId + ",day=" + day + "]";
    }
}