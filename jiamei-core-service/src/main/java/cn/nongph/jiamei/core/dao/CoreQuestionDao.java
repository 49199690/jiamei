package cn.nongph.jiamei.core.dao;

import cn.nongph.jiamei.core.domain.CoreOption;
import cn.nongph.jiamei.core.domain.CoreQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoreQuestionDao {

    CoreQuestion getQuestionById(Long id);

    void createQuestion( CoreQuestion question);

    void updateQuestion( CoreQuestion question);

    List<CoreQuestion> getQuestionsByQuestionnaire(Long questionnaireId);
    
    void createOption(CoreOption option );
    
    void updateOption(CoreOption option );
    
    void deleteOption(Long optionId);
    
    CoreOption getOptionById(Long optionId);
    
    List<CoreOption> getOptionsByQuestion(Long questionId);
}
