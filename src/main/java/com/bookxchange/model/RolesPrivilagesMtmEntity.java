package com.bookxchange.model;

import javax.persistence.*;

@Entity
@Table(name = "roles_privilages_mtm", schema = "bookOLX", catalog = "")
public class RolesPrivilagesMtmEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "role_id")
    private Integer roleId;
    @Basic
    @Column(name = "privilage_id")
    private Integer privilageId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPrivilageId() {
        return privilageId;
    }

    public void setPrivilageId(Integer privilageId) {
        this.privilageId = privilageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesPrivilagesMtmEntity that = (RolesPrivilagesMtmEntity) o;

        if (id != that.id) return false;
        if (roleId != null ? !roleId.equals(that.roleId) : that.roleId != null) return false;
        if (privilageId != null ? !privilageId.equals(that.privilageId) : that.privilageId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (roleId != null ? roleId.hashCode() : 0);
        result = 31 * result + (privilageId != null ? privilageId.hashCode() : 0);
        return result;
    }
}
