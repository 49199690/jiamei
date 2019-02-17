package cn.nongph.jiamei.core.domain;

import java.util.Date;
import java.util.List;

import cn.nongph.jiamei.core.service.CoreAnswerService;
import cn.nongph.jiamei.core.service.CoreCooperationService;

public class CoreAnswer {
	public static enum FROM_TYPE{
		COOPERATION
	}
	
	private Long id;
	
	private CoreUser user;
	
	private CoreQuestionnaire questionnaire;
	
	private Date startTime;
	
	private Date submitTime;
	
	private Integer score;
	
	private String fromType;
	
	private Long fromId;
	
	private CoreEvaluate evaluate;
	
	private List<CoreQuestionAnswer> questionAnswers;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CoreUser getUser() {
		return user;
	}

	public void setUser(CoreUser user) {
		this.user = user;
	}

	public CoreQuestionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(CoreQuestionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getFromType() {
		return fromType;
	}

	public void setFromType(String fromType) {
		this.fromType = fromType;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public CoreEvaluate getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(CoreEvaluate evaluate) {
		this.evaluate = evaluate;
	}

	public String getFromDesc() {
		if( FROM_TYPE.COOPERATION.name().equals( getFromType() ) ) {
			return CoreCooperationService.instance.getCooperationById( getFromId() ).getName();
		} else {
			return "";
		}
	}
	
	public List<CoreQuestionAnswer> getQuestionAnswers() {
		if(questionAnswers==null)
			questionAnswers = CoreAnswerService.instance.getQuestionAnswersByAnswer(id);
		return questionAnswers;
	}

	public void setQuestionAnswers(List<CoreQuestionAnswer> questionAnswers) {
		this.questionAnswers = questionAnswers;
	}
}
