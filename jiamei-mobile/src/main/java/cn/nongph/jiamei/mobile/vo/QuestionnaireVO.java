package cn.nongph.jiamei.mobile.vo;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireVO extends QuestionnaireBaseVO{
	private String state;
	
	private String desc;
	
	private List<QuestionVO> questions = new ArrayList<QuestionVO>();
	
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

	public List<QuestionVO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionVO> questions) {
		this.questions = questions;
	}
}