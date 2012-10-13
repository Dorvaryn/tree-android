package com.garden.thefiletree.api;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TreeResponse {
	protected List<TreeFile> files;
	protected Error err;

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
	
	public TreeResponse(List<TreeFile> files) {
		super();
		this.files = files;
	}

	public TreeResponse() {
		super();
	}

	public List<TreeFile> getFiles() {
		return files;
	}

	public void setFiles(List<TreeFile> files) {
		this.files = files;
	}
	
	public Error getErr() {
		return err;
	}

	public void setErr(Error err) {
		this.err = err;
	}

	public TreeFile getFile(){
		if(files.get(0).isDirectory()){
			return getDir();
		}else if(files.get(0).isText()){
			return getTextFile();
		}else{
			return files.get(0);
		}
	}
	
	private TreeTextFile getTextFile(){
		if(files.get(0).isText()){
			return new TreeTextFile(files.get(0));
		}
		return null;
	}
	
	private TreeDirectory getDir(){
		if(files.get(0).isDirectory()){
			return new TreeDirectory(files.get(0), files.subList(1, files.size()));
		}
		return null;
	}
	
}
