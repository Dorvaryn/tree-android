package com.garden.thefiletree.api;


public class TreeTextFile extends TreeFile {

	public TreeTextFile(TreeFile file) {
		super(file.getMeta(), file.getPath(), (String) file.getContent(), file.getErr());
	}

	public String getContent() {
		return (String)content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
