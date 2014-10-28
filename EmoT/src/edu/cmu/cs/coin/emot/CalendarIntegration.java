package edu.cmu.cs.coin.emot;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Instances;
import android.util.Log;

public class CalendarIntegration extends Activity {
	//The goal of this function is to read all the calendars that
	// are available on the device
	// Partly for testing
	//Code copied from tutorial at http://www.grokkingandroid.com/androids-calendarcontract-provider/
	public void getAllCalendars() {
		String[] projection =
				new String[]{
				Calendars._ID,      //ID for the row
				Calendars.NAME,     //Column name, name of the calendar
				Calendars.ACCOUNT_NAME,  //Account used to sync entry to device
				Calendars.ACCOUNT_TYPE   //Type of account used to sync entry to device
				};
		
		Cursor calCursor = 
				getContentResolver().
				query(Calendars.CONTENT_URI, 
						projection, //Defined above, these are the columns we want to return
						Calendars.VISIBLE + " = 1", //Show events associated with this calendar
						null, 
						Calendars._ID + " ASC");
		
		if(calCursor.moveToFirst()) {  //Will enter this if statement if cursor isn't empty
			do {
				//Returns value of first column as a long int
				long id = calCursor.getLong(0);
				String displayName = calCursor.getString(1);
				String accountName = calCursor.getString(2);
				String accountType = calCursor.getString(3);
			} while(calCursor.moveToNext());
		}
	}

	
	private static void printInstances(Cursor instanceCursor) {
		if(instanceCursor.moveToFirst()) {
			do {
				String title = instanceCursor.getString(TITLE_INDEX);
				String description = instanceCursor.getString(DESCRIPTION_INDEX);
				Log.d("Event info", title + " " + description);
			} while(instanceCursor.moveToNext());
		}
	}

	//Dear lord please forgive my horrible style sins
	private static int BEGIN_INDEX = 0;
	private static int END_INDEX = 1;
	private static int EVENT_ID_INDEX = 2;
	private static int CALENDAR_DISPLAY_NAME_INDEX = 3;
	private static int TITLE_INDEX = 4;
	private static int AVAILABILITY_INDEX = 5;
	private static int DESCRIPTION_INDEX = 6;
	
	public static Cursor getInstances(ContentResolver cr, long startMil, long endMil) {		
		Log.d("Event info", "Entered getInstances!!");
		
		//This function will return a cursor that contains all visible instances in the
		// given thread
		String[] projection = 
				new String[] {
				Instances.BEGIN,           //Beginning time of instance, in UTC milliseconds
				Instances.END,             //End time, in UTC milliseconds
				Instances.EVENT_ID,        //_id of the event FOR THIS INSTANCE
				Instances.CALENDAR_DISPLAY_NAME,  //Display name of the calendar
				Instances.TITLE,           //Title of the event
				Instances.AVAILABILITY,    //Are they busy, free, or tentatively free during this event
				Instances.DESCRIPTION      //For potential display purposes later
				};
		
		Cursor gapCursor = 
				Instances.query(
						cr,
						projection,
						startMil,
						endMil);
		
		printInstances(gapCursor);
		return gapCursor;
	}
}
