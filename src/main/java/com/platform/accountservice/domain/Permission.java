package com.platform.accountservice.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "permissions")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Permission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "permission_description")
    private String permissionDescription;

    @ManyToMany(mappedBy = "rolePermissions")
    @Builder.Default
    @JsonManagedReference
    private List<Role> userList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o instanceof Permission) {
            Permission permission = (Permission) o;
            return this.permissionName.equals(permission.getPermissionName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissionName);
    }

    @Override
    public String toString() {
        return "Role{" +
                "permissionName='" + permissionName + '\'' +
                ", permissionDescription='" + permissionDescription + '\'' +
                '}';
    }

    public String getPermissionName() {
        return permissionName;
    }


//	public void setPermissionDescription(String permissionDescription) {
//		this.permissionDescription = permissionDescription;
//	}
//
////	public List<Role> getUserList() {
////		return userList;
////	}
////
////	public void setUserList(List<Role> userList) {
////		this.userList = userList;
////	}

}
