package com.garden.thefiletree.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.garden.thefiletree.TheFileTreeApp;
import com.garden.thefiletree.parser.TreeJsonParser;

public class TreeAPI {
	private TreeFile file;

	public void treeGetFile(String path) {
		file = TheFileTreeApp.getFileFromMemCache(path);
		if(file == null){
			httpReadFile(path);
		}else {
			HttpEntity respEntity = makeHttpCall("date", path);
			InputStream data = null;
			try {
				data = respEntity.getContent();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TreeJsonParser parser = new TreeJsonParser();
			TreeFile dateFile = parser.parseStreamAsFile(data);
			
			if(!dateFile.getDate().equals(file.getDate())){
				httpReadFile(path);
			}
		}
	}
	
	private void httpReadFile(String path){
		HttpEntity respEntity = makeHttpCall("read", path);
		InputStream data = null;
		try {
			data = respEntity.getContent();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TreeJsonParser parser = new TreeJsonParser();
		file = parser.parseStream(data).getFile();
		TheFileTreeApp.addFileToMemCache(file);
	}

	public TreeFile getFile(){
		return file;
	}
	
	public TreeTextFile getTextFile(){
		if(file.isText()){
			return (TreeTextFile) file;
		}
		return null;
	}
	
	public TreeDirectory getDir(){
		if(file.isDirectory()){
			return (TreeDirectory) file;
		}
		return null;
	}
	
	/*public Boolean isDirectory(){
		return files.get(0).isDirectory();
	}
	
	public Boolean isText(){
		return files.get(0).isText();
	}*/

	public HttpEntity makeHttpCall(String op, String path) {
		
		HttpClient client = new DefaultHttpClient();
		//HttpPost post = new HttpPost("https://thefiletree.com/$fs");
		HttpPost post = new HttpPost("http://192.168.1.3/$fs");
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("op", "\""+op+"\""));
		pairs.add(new BasicNameValuePair("path", "\""+ path + "\""));
	
		
		try {
			post.setEntity(new UrlEncodedFormEntity(pairs, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HttpResponse response = null;
		try {
			response = client.execute(post);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response.getEntity();
	}
}
