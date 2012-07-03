package com.garden.thefiletree;

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

public class TreeAPI {
	private TreeFile file = null;

	public void treeGetFile(String path) {
		file = TheFileTreeApp.getFileFromMemCache(path);
		if(file == null){
			HttpEntity respEntity = makeHttpCall(path);
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
			file = parser.parseStream(data);
			TheFileTreeApp.addFileToMemCache(file);
		}
	}

	public TreeDirectory getDir() {
		if (file.isDirectory()) {
			@SuppressWarnings("unchecked")
			TreeDirectory ret = new TreeDirectory(file.getMeta(), file.getPath(), (List<String>) file.getContent());
			return ret;
		}
		return null;
	}

	public TreeTextFile getTextFile() {
		if (file.isText()) {
			TreeTextFile ret = new TreeTextFile(file.getMeta(), file.getPath(), (String) file.getContent());
			return ret;
		}
		return null;
	}
	
	public TreeFile getFile() {
		return file;
	}

	public HttpEntity makeHttpCall(String path) {
		
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://thefiletree.com/$fs");
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		pairs.add(new BasicNameValuePair("op", "\"cat\""));
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
