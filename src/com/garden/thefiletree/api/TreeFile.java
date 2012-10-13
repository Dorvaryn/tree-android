package com.garden.thefiletree.api;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TreeFile {
	protected Meta meta;
	protected String path;
	protected Date date;
	protected Object content;
	protected Error err;

	@JsonIgnoreProperties(ignoreUnknown=true)
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
	
	@JsonIgnoreProperties(ignoreUnknown=true)
	public static class Error{
		private int errno;
		private String code;
		private String path;
		public int getErrno() {
			return errno;
		}
		public void setErrno(int errno) {
			this.errno = errno;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
	}

	public TreeFile(Meta meta, String path, Date date, Object content, Error err) {
		super();
		this.meta = meta;
		this.path = path;
		this.date = date;
		this.content = content;
		this.err = err;
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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	public Error getErr() {
		return err;
	}

	public void setErr(Error err) {
		this.err = err;
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
