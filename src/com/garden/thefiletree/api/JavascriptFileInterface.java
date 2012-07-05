package com.garden.thefiletree.api;

import android.content.Context;

public class JavascriptFileInterface {

	private TreeTextFile file;

	public JavascriptFileInterface(TreeTextFile file) {
		this.file = file;
	}

	public String getContent() {
		return file.getContent();
	}
	
	public String getMime() {
		return file.getMeta().getType();
	}
	
	public String getTheme() {
		return file.getMeta().getTheme();
	}
	
	public String getPath() {
		return file.getPath();
	}

}
