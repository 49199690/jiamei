package cn.nongph.jiamei.mobile.vo;

import java.util.ArrayList;
import java.util.List;

public class QuestionVO{
	private Long id;

	private Integer sequence;
    
	private String type;

	private String content;
	
	private List<OptionVO> options = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
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

	public List<OptionVO> getOptions() {
		return options;
	}

	public void setOptions(List<OptionVO> options) {
		this.options = options;
	}
}
