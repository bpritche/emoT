package edu.cmu.cs.coin.emot;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentPatternTab extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, 
            Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_fragment_pattern_tab, container, false);
		TextView textview = (TextView) view.findViewById(R.id.hello_text);
		textview.setText(R.string.hello_world);
		return view;
	}
	
	

}
