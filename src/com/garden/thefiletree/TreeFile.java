package com.garden.thefiletree;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties({"err"})
public class TreeFile {
	protected Meta meta;
	protected String path;
	protected Object content;

	public static class Meta {
		private String type;
		private String theme;

		public String getTheme() {
			return theme;
		}

		public void setTheme(String theme) {
			this.theme = theme;
		}

		public String getType() {
			return type;
		}

		public void setType(String s) {
			type = s;
		}
	}

	public TreeFile(Meta meta, String path, Object content) {
		super();
		this.meta = meta;
		this.path = path;
		this.content = content;
	}

	public TreeFile() {
		super();
	}

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	public boolean isDirectory() {
		if(meta != null){
			return meta.type.equalsIgnoreCase("dir");
		}
		return false;
	}

	public boolean isText() {
		if(meta != null){
			return meta.type.startsWith("text/");
		}
		return false;
	}
	
	public boolean isImage() {
		if(meta != null){
			return meta.type.startsWith("image/");
		}
		return false;
	}
}
