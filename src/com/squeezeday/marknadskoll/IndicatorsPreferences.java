package com.squeezeday.marknadskoll;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


public class IndicatorsPreferences
	extends PreferenceActivity
{
	
	@Override
	protected void onStop() {

		Intent update = new Intent(this, Indicators.class);
		update.setAction(Indicators.REFRESH);
		sendBroadcast(update);
		
		super.onStop();
	};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        
        Intent intent=getIntent();
		Bundle extras=intent.getExtras();

		int id = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		
		Intent result=new Intent();
		result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,id);
		setResult(RESULT_OK, result);
        
    }
    
	public static int loadTextColor(Context c) {
		return (PreferenceManager.getDefaultSharedPreferences(c).getBoolean("black_color", false) == false ? Color.WHITE : Color.BLACK);
	}
}
