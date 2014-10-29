package edu.cmu.cs.coin.emot;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import edu.cmu.cs.coin.emot.DatabaseContract.EmotionData;

public class FragmentHomeTab extends Fragment implements OnClickListener {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fragment_home_tab, container, false);
		TextView textview = (TextView) view.findViewById(R.id.text1);
		textview.setText(R.string.feeling_prompt);
		Log.d("Debug", "Gets past setting text");
		view.findViewById(R.id.button1).setOnClickListener(this);
		return view;
	}
	
	protected static final int REQUEST_OK = 1;
	protected static final int RESULT_OK = 1;
	
	//Create new database
	SQLiteDatabase emotionDB;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		DatabaseHelper dbHelper = new DatabaseHelper(activity);
		emotionDB = dbHelper.getWritableDatabase();
	}
	
	@Override
	public void onClick(View v) {
	Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	         i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
	        	 try {
	             startActivityForResult(i, REQUEST_OK);
	         } catch (Exception e) {
	        	 	Toast.makeText(getActivity(), "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
	         }
	}
	
	public void checkDatabase() {
		Cursor emotionCursor = emotionDB.query(EmotionData.TABLE_NAME, null, null, null, null, null, null);
	
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
		CalendarIntegration.getInstances(getActivity().getContentResolver(), systemTime-1, systemTime+1);
		
		ContentValues values = new ContentValues();
		values.put(EmotionData.TIME, systemTime);
		values.put(EmotionData.EMOTION_TEXT, emotionText);
		
		long newRowID = emotionDB.insert(EmotionData.TABLE_NAME, null, values);
		return newRowID;
	}	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (requestCode==REQUEST_OK  && resultCode==RESULT_OK) {
	        		ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	        		String emotionText = thingsYouSaid.get(0);
	        		((TextView)getView().findViewById(R.id.text1)).setText(emotionText);
	        		//Put this data into the database
	        		insertEmotionData(emotionText);
	        		
	        		checkDatabase();
	        }
	        
	}
}
