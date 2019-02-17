package cn.nongph.jiamei.core.service;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.nongph.jiamei.core.dao.CoreAnswerDao;
import cn.nongph.jiamei.core.domain.CoreAnswer;
import cn.nongph.jiamei.core.domain.CoreQuestionAnswer;

@Service
public class CoreAnswerService {
	
	@Resource
	private CoreAnswerDao dao;
	
	public void createAnswer(CoreAnswer answer) {
		dao.createAnswer( answer );
		
		answer.getQuestionAnswers().forEach( qa->{
			qa.setAnswer( answer );
			dao.createQuestionAnswer( qa );
		});
	}
	
	public void updateEvaluate(CoreAnswer answer) {
		dao.updateEvaluate(answer.getId(), answer.getEvaluate().getId());
	}
	
	public CoreAnswer getAnswerById(Long id) {
		return dao.getAnswerById(id);
	}
	
	public List<CoreQuestionAnswer> getQuestionAnswersByAnswer(Long id){
		return dao.getQuestionAnswersByAnswer(id);
	}
	
	public PageList<CoreAnswer> findAnswers(PageBounds pageBounds, Long questionnaireId, 
			                                Date submitStart, Date submitEnd, Long cooperationId){
		return dao.findAnswers(pageBounds, questionnaireId, submitStart, submitEnd, cooperationId);
	}
	
	public static CoreAnswerService instance;
	
	@PostConstruct
	public void setInstance() {
		instance = this;
	}
}
