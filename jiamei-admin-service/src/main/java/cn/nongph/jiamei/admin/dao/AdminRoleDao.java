package cn.nongph.jiamei.admin.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.nongph.jiamei.admin.domain.AdminRole;

import java.util.List;

@Repository
public interface AdminRoleDao{

    public List<AdminRole> getRolesByUser(@Param("userid") Long userid);

    public List<AdminRole> findAllRoles();
}
