package edu.cmu.cs.coin.emot;

import android.provider.BaseColumns;

public final class DatabaseContract {
	
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "emotionDatabase.db";
	private static final String TEXT_TYPE = " TEXT";
	private static final String NUM_TYPE = " LONG";
	private static final String COMMA_SEP = ",";
	
	//So someone doesn't accidentally instantiate the contract class
	public DatabaseContract() {}
	
	public static abstract class EmotionData implements BaseColumns {
		public static final String TABLE_NAME = "emotionTracker";
		public static final String TIME = "timeStamp";
		public static final String EMOTION_TEXT = "emotionText";
		public static final String EMOTION_NAME = "emotion";
	
		public static final String SQL_CREATE_ENTRIES = 
				"CREATE TABLE " + TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," +
			    TIME + NUM_TYPE + COMMA_SEP + EMOTION_TEXT + TEXT_TYPE + COMMA_SEP +
			    EMOTION_NAME + TEXT_TYPE + ");";
		
		public static final String SQL_DELETE_ENTRIES = 
				"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
}
