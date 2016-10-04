package com.dbeef.speechlist.utils;

public class Variables {

	public static final int CHARACTER_WIDTH = 19;
	public static final int MAX_CHAR_PER_TEST_LINE = 29;
	public static final int MAX_LINES_PER_TEST_SCREEN = 11;
	public static final int GUI_CAMERA_BRIEF_POSITION = 1175;
	public static final int GUI_CAMERA_POSITION = 720;
	public static final int INITIAL_SCREEN_POSITION = 240;
	public static final int HOME_SCREEN_POSITION = 720;
	public static final int TESTS_SCREEN_POSITION = 1200;
	public static final int DOWNLOADS_SCREEN_POSITION = 1680;
	public static final int BRIEF_SCREEN_POSITION = 2160;
	public static final int SPHINX_SCREEN_POSITION = 2640;
	public static final int SOLVING_SCREEN_POSITION = 2400;
	public static final int SOLVING_SCREEN_VOCABULARY_POSITION_X = SOLVING_SCREEN_POSITION + 10;
	public static final int SOLVING_SCREEN_VOCABULARY_POSITION_Y = 735;
	public static final int SOLVING_SCREEN_VOCABULARY_SPAN_Y = 60;
	public static final int SCREEN_WIDTH = 480;

	public static final String WEBSERVICE_ADRESS = "http://155.133.44.54:8080/UserManagement/rest/TestService/";

	public static final String HEADER_APPLICATION_JSON = "application/json";
	public static final String HEADER_TEXT_PLAIN = "text/plain";
	
	public static final String TASK_RETRIEVE_TEST = "RETRIEVE_TEST";
	public static final String TASK_RETRIEVE_TEST_NAME = "RETRIEVE_TEST_NAME";
	public static final String TASK_RETRIEVE_TESTS_NAMES_CONTAINER = "TASK_RETRIEVE_TESTS_NAMES_CONTAINER";	
	
	public static final String CATEGORY_VOCABULARY = "vocabulary";
	public static final String CATEGORY_IDIOMS = "idioms";
	public static final String CATEGORY_TENSES = "tenses";
	public static final String CATEGORY_VARIOUS = "various";
	public static final String CATEGORY_DOWNLOADABLE = "downloadable";

	public static final String INITIAL_TEXT_LOADING_ASSETS = "loading assets...";
	public static final String INITIAL_TEXT_ARGUING_WITH_SERVER = "arguing with server...";
	public static final String INITIAL_TEXT_FAILED_TO_CONNECT_TO_SERVER = "failed to connect tests server...";
	
	public static final boolean DEBUG_MODE = true;
	public static final boolean DEBUG_INPUT = false;

}