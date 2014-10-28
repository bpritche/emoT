package edu.cmu.cs.coin.emot;

import java.util.ArrayList;
import edu.cmu.cs.coin.emot.R;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import edu.cmu.cs.coin.emot.DatabaseContract.EmotionData;

public class MainActivity extends Activity implements OnClickListener {

	protected static final int REQUEST_OK = 1;
	
	//Create new database
	protected SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.button1).setOnClickListener(this);
		
		DatabaseHelper dbHelper = new DatabaseHelper(this.getApplicationContext());
		db = dbHelper.getWritableDatabase();
	}
	
	@Override
	public void onClick(View v) {
	Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	         i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
	        	 try {
	             startActivityForResult(i, REQUEST_OK);
	         } catch (Exception e) {
	        	 	Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
	         }
	}
	
	public void checkDatabase() {
		Cursor emotionCursor = db.query(EmotionData.TABLE_NAME, null, null, null, null, null, null);
	
		// Iterate over the rows
		int id = 1;
		while (emotionCursor.moveToNext()) {
			String debugStr = "Database lookup" + Integer.toString(id);
			
			//Pull out the value of each column - for now, we're just looking
			// at the time and the text given, though we'll have more in the future
			long timeStamp = emotionCursor.getLong(
					emotionCursor.getColumnIndex(EmotionData.TIME));
			Log.d(debugStr, "Timestamp " + Long.toString(timeStamp));
			
			String emotionText = emotionCursor.getString(
					emotionCursor.getColumnIndex(EmotionData.EMOTION_TEXT));
			Log.d(debugStr, "Emotion text " + emotionText);
			
			id++;
		}
	}
	
	public long insertEmotionData(String emotionText) {
		
		//This will give us the current time in milliseconds since Jan. 1, 1970, 00:00 UTC
		// KEEP THIS IN MIND when looking up this data later
		long systemTime = System.currentTimeMillis();
		//For now, I'm just using this to test that our calendar is working
		CalendarIntegration.getInstances(this.getContentResolver(), systemTime-1, systemTime+1);
		
		ContentValues values = new ContentValues();
		values.put(EmotionData.TIME, systemTime);
		values.put(EmotionData.EMOTION_TEXT, emotionText);
		
		long newRowID = db.insert(EmotionData.TABLE_NAME, null, values);
		return newRowID;
	}	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
	        		ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	        		String emotionText = thingsYouSaid.get(0);
	        		((TextView)findViewById(R.id.text1)).setText(emotionText);
	        		//Put this data into the database
	        		insertEmotionData(emotionText);
	        		
	        		checkDatabase();
	        }
	        
	    }


}