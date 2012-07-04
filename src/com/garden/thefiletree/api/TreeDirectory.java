package com.garden.thefiletree.api;

import java.util.List;

public class TreeDirectory extends TreeFile{
	
	@SuppressWarnings("unchecked")
	public TreeDirectory(TreeFile file) {
		super(file.getMeta(), file.getPath(), (List<String>) file.getContent(), file.getErr());
	}
	@SuppressWarnings("unchecked")
	public List<String> getContent() {
		return (List<String>) content;
	}
	public void setContent(List<String> content) {
		this.content = content;
	}

}
