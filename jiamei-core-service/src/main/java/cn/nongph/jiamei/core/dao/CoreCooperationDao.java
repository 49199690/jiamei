package cn.nongph.jiamei.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.nongph.jiamei.core.domain.CoreCooperation;

@Repository
public interface CoreCooperationDao {
	
	void createCooperation(CoreCooperation coop);
	
	void updateCooperation(CoreCooperation coop);
	
	List<CoreCooperation> getAllCooperations();
	
	CoreCooperation getCooperationById(Long id);
}
