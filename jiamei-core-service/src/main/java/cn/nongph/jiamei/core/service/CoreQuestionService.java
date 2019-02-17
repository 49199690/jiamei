package cn.nongph.jiamei.core.service;

import cn.nongph.jiamei.core.dao.CoreQuestionDao;
import cn.nongph.jiamei.core.domain.CoreOption;
import cn.nongph.jiamei.core.domain.CoreQuestion;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Service
public class CoreQuestionService {

    @Resource
    private CoreQuestionDao dao;

    public void createQuestion(CoreQuestion question) {
    	dao.createQuestion(question);
    	question.getOptions().forEach( o->{
    		o.setQuestion( question );
    		dao.createOption( o );
    	} );
    }

    public void modifyQuestion(CoreQuestion question) {
    	dao.updateQuestion(question);
    }
    
    public void createOption(CoreOption option) {
    	dao.createOption( option );
    }
    
    public void modifyOption(CoreOption option) {
    	dao.updateOption(option);
    }
    
    public void deleteOption(Long optionId) {
    	dao.deleteOption(optionId);
    }
    
    public CoreQuestion getQuestionById(Long id) {
    	return dao.getQuestionById(id);
    }
    
    public List<CoreQuestion> getQuestionsByQuestionnaire(Long questionnaireId){
        return dao.getQuestionsByQuestionnaire(questionnaireId);
    }
    
    public CoreOption getOptionById(Long id) {
    	return dao.getOptionById(id);
    }
    
    public List<CoreOption> getOptionsByQuestion(Long questionId){
    	return dao.getOptionsByQuestion(questionId);
    }
    
    public static CoreQuestionService instance;

    @PostConstruct
    public void setInstance(){
        instance=this;
    }

}
