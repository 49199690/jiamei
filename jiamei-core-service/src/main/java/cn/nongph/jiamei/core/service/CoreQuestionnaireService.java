package cn.nongph.jiamei.core.service;

import cn.nongph.jiamei.core.dao.CoreQuestionnaireDao;
import cn.nongph.jiamei.core.domain.CoreQuestionnaire;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Service
public class CoreQuestionnaireService {

    @Resource
    private CoreQuestionnaireDao dao;

    public PageList<CoreQuestionnaire> findQuestionnaire(PageBounds pageBounds, String key){
        if( StringUtils.isBlank(key) )
            key = null;
        return dao.findQuestionnaires(pageBounds, key);
    }

    public void createQuestionnaire( CoreQuestionnaire q ){
        dao.createQuestionnaire( q );
    }

    public void updateQuestionnaire( CoreQuestionnaire q ){
        dao.updateQuestionnaire(q);
    }

    public CoreQuestionnaire getQuestionnaireById(Long id){
        return dao.getQuestionnaireById(id);
    }
    
    public boolean releaseQuestionnaire(Long id) {
    	return dao.changeQuestionnaireState( id, 
    			                             CoreQuestionnaire.STATE.PREPARE.name(), 
    			                             CoreQuestionnaire.STATE.RELEASE.name() 
    			                            )>0;
    }
    
    public boolean closeQuestionnaire(Long id) {
    	return dao.changeQuestionnaireState( id, 
    			                             CoreQuestionnaire.STATE.RELEASE.name(), 
    			                             CoreQuestionnaire.STATE.CLOSED.name() 
    			                            )>0;
    }
    
    
    public List<CoreQuestionnaire> getUsableQuestionnaire(){
    	return dao.findQuestionnairesByStates( CoreQuestionnaire.STATE.RELEASE.name(),
    										   CoreQuestionnaire.STATE.LOCKED.name() );
    }
    
    public List<CoreQuestionnaire> getUnclosedQuestionnaire(){
    	return dao.findQuestionnairesByStates( CoreQuestionnaire.STATE.PREPARE.name(),
    			                               CoreQuestionnaire.STATE.RELEASE.name(),
				   							   CoreQuestionnaire.STATE.LOCKED.name() );
    }
    
    public void setNextVersion(Long id, Long nextId) {
    	dao.setNextVersion(id, nextId);
    }
    
    public static CoreQuestionnaireService instance;
    
    @PostConstruct
    public void setInstance() {
    	instance = this;
    }
}
