package com.trustbank.Model;

import java.io.Serializable;

public class DynamicMenuModel implements Serializable {
    public String menucode;
    public String submenucode;
    public String caption;



    public String getMenucode() {
        return menucode;
    }

    public void setMenucode(String menucode) {
        this.menucode = menucode;
    }

    public String getSubmenucode() {
        return submenucode;
    }

    public void setSubmenucode(String submenucode) {
        this.submenucode = submenucode;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
