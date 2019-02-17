package cn.nongph.jiamei.core.domain;

import cn.nongph.jiamei.core.annotation.RequiredField;
import cn.nongph.jiamei.core.service.CoreEvaluateService;

public class CoreEvaluate extends BasicDomain{
	private Long  id;
	
	private String name;
	
	@RequiredField
	private String rule;
	
	private String result;
	
	private CoreQuestionnaire questionnaire;
	
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

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public String getResult() {
		loadIfNecessary();
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public CoreQuestionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(CoreQuestionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	@Override
	protected BasicDomain doLoad() {
		return CoreEvaluateService.instance.getEvaluateById(id);
	}
}
