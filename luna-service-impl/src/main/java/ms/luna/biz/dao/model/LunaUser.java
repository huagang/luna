package ms.luna.biz.dao.model;

import com.alibaba.dubbo.common.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import ms.luna.biz.table.LunaUserTable;

import java.io.Serializable;
import java.util.Date;
@JSONType(ignores = {"createTime"})
public class LunaUser implements Serializable {
    @JSONField(name = LunaUserTable.FIELD_ID)
    private String uniqueId;
    @JSONField(name = LunaUserTable.FIELD_LUNA_NAME)
    private String lunaName;
    @JSONField(name = LunaUserTable.FIELD_NICK_NAME)
    private String nickName;

    private String password;
    @JSONField(name = LunaUserTable.FIELD_HEAD_IMG_URL)
    private String headImgUrl;

    private String email;
    @JSONField(name = LunaUserTable.FIELD_CREATE_TIME)
    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId == null ? null : uniqueId.trim();
    }

    public String getLunaName() {
        return lunaName;
    }

    public void setLunaName(String lunaName) {
        this.lunaName = lunaName == null ? null : lunaName.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        LunaUser other = (LunaUser) that;
        return (this.getUniqueId() == null ? other.getUniqueId() == null : this.getUniqueId().equals(other.getUniqueId()))
            && (this.getLunaName() == null ? other.getLunaName() == null : this.getLunaName().equals(other.getLunaName()))
            && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getHeadImgUrl() == null ? other.getHeadImgUrl() == null : this.getHeadImgUrl().equals(other.getHeadImgUrl()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUniqueId() == null) ? 0 : getUniqueId().hashCode());
        result = prime * result + ((getLunaName() == null) ? 0 : getLunaName().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getHeadImgUrl() == null) ? 0 : getHeadImgUrl().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "LunaUser [uniqueId=" + uniqueId + ",lunaName=" + lunaName + ",nickName=" + nickName + ",password=" + password + ",headImgUrl=" + headImgUrl + ",email=" + email + ",createTime=" + createTime + ",updateTime=" + updateTime + "]";
    }

    public static void main(String[] args) {
        LunaUser lunaUser = new LunaUser();
        String[] arr = { LunaUserTable.FIELD_LUNA_NAME, LunaUserTable.FIELD_NICK_NAME};
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(LunaUser.class, arr);
        lunaUser.setLunaName("shawn");
        lunaUser.setNickName("shawn");
        lunaUser.setCreateTime(new Date());
//        System.out.println(JSON.toJSONString(lunaUser, simplePropertyPreFilter));
        System.out.println(JSON.toJSONString(lunaUser));
        SerializeConfig.globalInstance.addFilter(LunaUser.class, simplePropertyPreFilter);
        System.out.println(JSON.toJSON(lunaUser));
    }
}