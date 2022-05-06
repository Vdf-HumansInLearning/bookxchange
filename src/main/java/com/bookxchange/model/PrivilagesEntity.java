package com.bookxchange.model;

import javax.persistence.*;

@Entity
@Table(name = "privilages", schema = "bookOLX", catalog = "")
public class PrivilagesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "privilage_id")
    private int privilageId;
    @Basic
    @Column(name = "privilage_name")
    private String privilageName;

    public int getPrivilageId() {
        return privilageId;
    }

    public void setPrivilageId(int privilageId) {
        this.privilageId = privilageId;
    }

    public String getPrivilageName() {
        return privilageName;
    }

    public void setPrivilageName(String privilageName) {
        this.privilageName = privilageName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PrivilagesEntity that = (PrivilagesEntity) o;

        if (privilageId != that.privilageId) return false;
        if (privilageName != null ? !privilageName.equals(that.privilageName) : that.privilageName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = privilageId;
        result = 31 * result + (privilageName != null ? privilageName.hashCode() : 0);
        return result;
    }
}
