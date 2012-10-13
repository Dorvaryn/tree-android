package com.garden.thefiletree.api;

import java.util.List;

public class TreeDirectory extends TreeFile{
	
	public TreeDirectory(TreeFile file, List<TreeFile> content) {
		super(file.getMeta(), file.getPath(), file.getDate(), content, file.getErr());
	}
	@SuppressWarnings("unchecked")
	public List<TreeFile> getContent() {
		return (List<TreeFile>) content;
	}
	public void setContent(List<TreeFile> content) {
		this.content = content;
	}

}
