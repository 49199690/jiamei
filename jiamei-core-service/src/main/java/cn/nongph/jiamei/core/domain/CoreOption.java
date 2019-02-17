package cn.nongph.jiamei.core.domain;

import cn.nongph.jiamei.core.annotation.RequiredField;
import cn.nongph.jiamei.core.service.CoreQuestionService;

public class CoreOption extends BasicDomain{
    private Long id;

    private CoreQuestion question;
    
    @RequiredField
    private String sequence;
    
    private String content;

    private Integer score;
    
    public CoreOption() {
    }
    
    public CoreOption(Long id) {
    	this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoreQuestion getQuestion() {
    	loadIfNecessary();
        return question;
    }

    public void setQuestion(CoreQuestion question) {
        this.question = question;
    }

    public String getContent() {
    	loadIfNecessary();
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
    	loadIfNecessary();
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

	public String getSequence() {
		loadIfNecessary();
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	@Override
	protected BasicDomain doLoad() {
		return CoreQuestionService.instance.getOptionById(id);
	}
}
