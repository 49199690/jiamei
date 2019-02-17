package cn.nongph.jiamei.core.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.nongph.jiamei.core.domain.CoreEvaluate;

@Repository
public interface CoreEvaluateDao {
	
	void createEvaluate(CoreEvaluate evaluate);
	
	void updateEvaluate(CoreEvaluate evaluate);
	
	void deleteEvaluate(Long id);
	
	CoreEvaluate getEvaluateById(Long id);
	
	List<CoreEvaluate> getEvaluatesByQuestionnaire(Long questionnaireId);
	
}
