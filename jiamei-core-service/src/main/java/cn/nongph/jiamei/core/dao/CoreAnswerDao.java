package cn.nongph.jiamei.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import cn.nongph.jiamei.core.domain.CoreAnswer;
import cn.nongph.jiamei.core.domain.CoreQuestionAnswer;

@Repository
public interface CoreAnswerDao {
	
	void createAnswer(CoreAnswer answer);
	
	void createQuestionAnswer(CoreQuestionAnswer questionAnswer);
	
	void updateEvaluate(@Param("id")Long id, @Param("evaluateId") Long evaluateId);
	
	CoreAnswer getAnswerById(Long id);
	
	CoreQuestionAnswer getQuestionAnswerById(Long questionAnswerId);
	
	List<CoreQuestionAnswer> getQuestionAnswersByAnswer(Long id);
	
	public PageList<CoreAnswer> findAnswers(PageBounds pageBounds, @Param("questionnaireId")Long questionnaireId);
}
