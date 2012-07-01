package com.garden.thefiletree;

import java.util.List;

public class TreeDirectory extends TreeFile{
	
	public TreeDirectory(Meta meta, String path, List<String> content) {
		super(meta, path, content);
	}
	@SuppressWarnings("unchecked")
	public List<String> getContent() {
		return (List<String>) content;
	}
	public void setContent(List<String> content) {
		this.content = content;
	}

}
