package com.garden.thefiletree.parser;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

import com.garden.thefiletree.api.TreeFile;
import com.garden.thefiletree.api.TreeResponse;

public class TreeJsonParser{

    public TreeJsonParser() {
    	super();
    }

    public TreeResponse parseStream(InputStream data){
	    try {
			return new ObjectMapper().readValue(data, TreeResponse.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
    }
    
    public TreeFile parseStreamAsFile(InputStream data){
	    try {
			return new ObjectMapper().readValue(data, TreeFile.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
    }
    
}