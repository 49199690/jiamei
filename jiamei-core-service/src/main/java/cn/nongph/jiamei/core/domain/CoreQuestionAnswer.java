package cn.nongph.jiamei.core.domain;

public class CoreQuestionAnswer {
	private Long id;
	
	private CoreAnswer answer;
	
	private CoreQuestion question;
	
	private CoreOption choice;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CoreAnswer getAnswer() {
		return answer;
	}

	public void setAnswer(CoreAnswer answer) {
		this.answer = answer;
	}

	public CoreQuestion getQuestion() {
		return question;
	}

	public void setQuestion(CoreQuestion question) {
		this.question = question;
	}

	public CoreOption getChoice() {
		return choice;
	}

	public void setChoice(CoreOption choice) {
		this.choice = choice;
	}
}
