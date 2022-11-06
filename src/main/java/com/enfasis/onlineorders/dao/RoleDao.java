package com.enfasis.onlineorders.dao;

import com.enfasis.onlineorders.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {
    Role findRoleByName(String name);
}
