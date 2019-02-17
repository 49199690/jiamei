package cn.nongph.jiamei.core.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.nongph.jiamei.core.dao.CoreCooperationDao;
import cn.nongph.jiamei.core.domain.CoreCooperation;

@Service
public class CoreCooperationService {
	
	@Resource
	private CoreCooperationDao dao;
	
	public void createCooperation(CoreCooperation coop) {
		dao.createCooperation( coop );
	}
	
    public void updateCooperation(CoreCooperation coop) {
    	dao.updateCooperation( coop );
    }
	
	public List<CoreCooperation> getAllCooperations(){
		return dao.getAllCooperations();
	}
	
	public CoreCooperation getCooperationById(Long id) {
		return dao.getCooperationById(id);
	}
	
	public static CoreCooperationService instance;
	
	@PostConstruct
	public void setInstance() {
		instance = this;
	}
}
