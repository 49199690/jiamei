package cn.nongph.jiamei.core.domain;

import java.util.List;

import cn.nongph.jiamei.core.service.CoreQuestionService;

/**
 * 非标准答案题目
 *
 */
public class CoreQuestion {
    
	public static enum TYPE{
        FILL, //填空
        SELECT//单项选择
    }
    
    private Long id;

    private CoreQuestionnaire questionnaire;
    
    private Integer sequence;
    
    private String type;

    private String content;
    
    private List<CoreOption> options;
    
    public CoreQuestion() {
    }
    
    public CoreQuestion(Long id) {
    	this.id = id;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoreQuestionnaire getQuestionnaire() {
        return questionnaire;
    }

    public void setQuestionnaire(CoreQuestionnaire questionnaire) {
        this.questionnaire = questionnaire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public List<CoreOption> getOptions() {
		if( options==null )
			options = CoreQuestionService.instance.getOptionsByQuestion(id);
		return options;
	}

	public void setOptions(List<CoreOption> options) {
		this.options = options;
	}
}
