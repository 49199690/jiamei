package cn.nongph.jiamei.core.domain;

import java.util.Date;
import java.util.List;

import cn.nongph.jiamei.core.annotation.RequiredField;
import cn.nongph.jiamei.core.service.CoreEvaluateService;
import cn.nongph.jiamei.core.service.CoreQuestionService;
import cn.nongph.jiamei.core.service.CoreQuestionnaireService;

public class CoreQuestionnaire extends BasicDomain {

	public static enum STATE{
		PREPARE, RELEASE, LOCKED
	}

	private Long id;
	
	@RequiredField
	private String name;
	private Date createTime;
	private String state;
	private String desc;
	
	private List<CoreQuestion> questions;
	
	private List<CoreEvaluate> evaluates;
	
	public CoreQuestionnaire() {
	}
	
	public CoreQuestionnaire(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		loadIfNecessary();
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<CoreQuestion> getQuestions() {
		if( questions==null )
			questions = CoreQuestionService.instance.getQuestionsByQuestionnaire(id);
		return questions;
	}

	public void setQuestions(List<CoreQuestion> questions) {
		this.questions = questions;
	}

	public List<CoreEvaluate> getEvaluates() {
		if( evaluates==null )
			evaluates = CoreEvaluateService.instance.getEvaluatesByQuestionnaire( id );
		return evaluates;
	}

	public void setEvaluates(List<CoreEvaluate> evaluates) {
		this.evaluates = evaluates;
	}

	@Override
	protected BasicDomain doLoad() {
		return CoreQuestionnaireService.instance.getQuestionnaireById(id);
	}
}
