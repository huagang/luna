package ms.luna.biz.dao.custom.model;

import java.io.Serializable;

/**
 * Created by greek on 16/8/9.
 */
public class FarmFieldResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
