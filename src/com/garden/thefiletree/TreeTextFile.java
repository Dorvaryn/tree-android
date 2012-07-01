package com.garden.thefiletree;

public class TreeTextFile extends TreeFile {

	public TreeTextFile(Meta meta, String path, String content) {
		super(meta, path, content);
	}

	public String getContent() {
		return (String)content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
