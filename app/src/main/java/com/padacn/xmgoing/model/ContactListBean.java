package com.padacn.xmgoing.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class ContactListBean implements Serializable {

    /**
     * pid : 991
     * contactIds : [{"id":"31","name":"刘文君"},{"id":"32","name":"哈哈"}]
     */

    private String pid;
    private List<ContactIdsBean> contactIds;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<ContactIdsBean> getContactIds() {
        return contactIds;
    }

    public void setContactIds(List<ContactIdsBean> contactIds) {
        this.contactIds = contactIds;
    }

    public static class ContactIdsBean implements Serializable {
        /**
         * id : 31
         * name : 刘文君
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
