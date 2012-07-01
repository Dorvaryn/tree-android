package com.garden.thefiletree;

import android.app.Application;

public class TheFileTreeApp extends Application {
	
	private static TreeAPI api;
	private static String currentDirPath = "/test";
	private static String currentFilePath = null;
	
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
