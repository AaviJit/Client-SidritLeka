package com.platform.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.platform.accountservice.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRoleName(String name);

	Role findByRoleId(Integer id);
}
