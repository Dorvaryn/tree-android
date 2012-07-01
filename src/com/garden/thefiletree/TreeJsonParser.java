package com.garden.thefiletree;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;

public class TreeJsonParser{

    public TreeJsonParser() {
    	super();
    }

    public TreeFile parseStream(InputStream in) {
    	try{
	        return new ObjectMapper().readValue(in, TreeFile.class);
	    } catch(JsonParseException e) {
	        e.printStackTrace();
	    } catch(IOException e) {
	        e.printStackTrace();
	    }
	    return null;
    }
    
}