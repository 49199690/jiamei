package cn.nongph.jiamei.core.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.nongph.jiamei.core.domain.CoreQuestionnaire;

@Repository
public interface CoreQuestionnaireDao {
	
	public void createQuestionnaire(CoreQuestionnaire q);
	
	public void updateQuestionnaire(CoreQuestionnaire q);

	public void setNextVersion(@Param("id")Long id, @Param("nextId")Long nextId);
	
	CoreQuestionnaire getQuestionnaireById(Long id);

	PageList<CoreQuestionnaire> findQuestionnaires(PageBounds pageBounds, @Param("key")String key);
	
	public int changeQuestionnaireState(@Param("id") Long id, @Param("beforeState")String beforeState, @Param("newState") String newState );
	
	public List<CoreQuestionnaire> findQuestionnairesByStates(@Param("state")String... state);
}
