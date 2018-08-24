package com.padacn.xmgoing.model;

/**
 * Created by Administrator on 2018/6/16 0016.
 */

public class Childrenbean {
    private String name;
    private String sex;
    private String brith;

    public Childrenbean() {

    }

    public Childrenbean(String name, String sex, String brith) {
        this.name = name;
        this.sex = sex;
        this.brith = brith;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBrith() {
        return brith;
    }

    public void setBrith(String brith) {
        this.brith = brith;
    }
}
