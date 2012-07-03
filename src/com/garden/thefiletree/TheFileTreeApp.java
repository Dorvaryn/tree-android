package com.garden.thefiletree;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.support.v4.util.LruCache;

public class TheFileTreeApp extends Application {
	
	private static TreeAPI api;
	private static String currentDirPath = "";
	private static String currentFilePath = null;
	public static String TAG = "tree";
	private static LruCache<String, TreeFile> mMemoryCache;
	
	@Override
	public void onCreate() {
		super.onCreate();
		// Get memory class of this device, exceeding this amount will throw an
	    // OutOfMemory exception.
	    final int memClass = ((ActivityManager)getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();

	    // Use 1/8th of the available memory for this memory cache.
	    final int cacheSize = 1024 * 1024 * memClass / 8;

	    mMemoryCache = new LruCache<String, TreeFile>(cacheSize);
	}
	
	public static void addFileToMemCache(TreeFile file) {
	    if (file.getPath() != null && getFileFromMemCache(file.getPath()) == null) {
	        mMemoryCache.put(file.getPath(), file);
	    }
	}

	public static TreeFile getFileFromMemCache(String path) {
		if(path != null){
			return mMemoryCache.get(path);
		}
		return null;
	}
	
	public TheFileTreeApp() {
		super();
		api = new TreeAPI();
	}
	public static TreeAPI getApi() {
		return api;
	}
	public static void setApi(TreeAPI api) {
		TheFileTreeApp.api = api;
	}
	public static String getCurrentFilePath() {
		return currentFilePath;
	}
	public static void setCurrentFilePath(String currentFilePath) {
		TheFileTreeApp.currentFilePath = currentFilePath;
	}
	public static String getCurrentDirPath() {
		return currentDirPath;
	}
	public static void setCurrentDirPath(String currentDirPath) {
		TheFileTreeApp.currentDirPath = currentDirPath;
	}
}
