package cn.nongph.jiamei.core.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.nongph.jiamei.core.dao.CoreEvaluateDao;
import cn.nongph.jiamei.core.domain.CoreEvaluate;

@Service
public class CoreEvaluateService {
	
	@Resource
	private CoreEvaluateDao dao;
	
	public void createEvaluate(CoreEvaluate evaluate) {
		dao.createEvaluate(evaluate);
	}
	
	public void modifyEvaluate(CoreEvaluate evaluate) {
		dao.updateEvaluate(evaluate);
	}
	
	public void deleteEvaluate(Long id) {
		dao.deleteEvaluate(id);
	}
	
	public CoreEvaluate getEvaluateById(Long id) {
		return dao.getEvaluateById(id);
	}
	
	public List<CoreEvaluate> getEvaluatesByQuestionnaire(Long questionnaireId){
		return dao.getEvaluatesByQuestionnaire(questionnaireId);
	}
	
	public static CoreEvaluateService instance;

	@PostConstruct
	public void setInstance() {
		instance = this;
	}
}
