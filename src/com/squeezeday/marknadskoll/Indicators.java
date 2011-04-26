package com.squeezeday.marknadskoll;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;


public class Indicators extends AppWidgetProvider {
	
	public static final String REFRESH="com.squeezeday.marknadskoll.Indicators.REFRESH";
	public static final String ERROR="com.squeezeday.marknadskoll.Indicators.ERROR";
	
	
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if (REFRESH.equals(intent.getAction())) {
			Toast.makeText(context, context.getString(R.string.loading), Toast.LENGTH_SHORT).show();
			context.startService(new Intent(context, UpdateService.class));
		} else if (ERROR.equals(intent.getAction())) {
			Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_LONG).show();
		} else {
			super.onReceive(context, intent);
		}
	};
	
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        
        context.startService(new Intent(context, UpdateService.class));
    }

    public static final class UpdateService extends Service {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onCreate() {
            super.onCreate();
            IntentFilter filter = new IntentFilter();
            filter.addAction(REFRESH);
            //this.registerReceiver(receiver, filter);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onDestroy() {
            super.onDestroy();
            //unregisterReceiver(receiver);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onStart(Intent intent, int startId) {
            super.onStart(intent, startId);
            update();
            
        }
    

        /**
         * {@inheritDoc}
         */
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        
        private int getColorByValue(double value, int textColor) {
        	int h = 255;
            int l = 230;
            if (textColor == Color.BLACK) {
            	h = 80;
            	l = 0;
            }
        	if (value > 0) return Color.rgb(l,h,l);
        	if (value < 0) return Color.rgb(h,l,l);
        	return textColor;
        }
        
        /**
         * Updates and redraws the Widget.
         */
        private void update() {
        	
        	Log.w("update", "updating");
        	
        	
        	int color = IndicatorsPreferences.loadTextColor(this);
        	
        	RemoteViews views = new RemoteViews(getPackageName(), R.layout.indicators);
        	
        	ComponentName  widget = new ComponentName(this, Indicators.class);
            AppWidgetManager  manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(widget, views);

            try {
	        	Indicator[] data = SixHelper.getIndicators();
	        	
	        	views.setTextColor(R.id.ind1_key, color);
	            views.setTextColor(R.id.ind2_key, color);
	            views.setTextColor(R.id.ind3_key, color);
	            views.setTextColor(R.id.ind4_key, color);
	            views.setTextColor(R.id.ind5_key, color);
	            views.setTextColor(R.id.ind6_key, color);
	            views.setTextColor(R.id.ind7_key, color);
	            views.setTextColor(R.id.ind1_val, color);
	            views.setTextColor(R.id.ind2_val, color);
	            views.setTextColor(R.id.ind3_val, color);
	            views.setTextColor(R.id.ind4_val, color);
	            views.setTextColor(R.id.ind5_val, color);
	            views.setTextColor(R.id.ind6_val, color);
	            views.setTextColor(R.id.ind7_val, color);
	        	
	            views.setTextViewText(R.id.ind1_key, data[0].title);
	            views.setTextViewText(R.id.ind1_val, data[0].valueToString());
	            views.setTextColor(R.id.ind1_val, getColorByValue(data[0].value, color));
	            
	            views.setTextViewText(R.id.ind2_key, data[1].title);
	            views.setTextViewText(R.id.ind2_val, data[1].valueToString());
	            views.setTextColor(R.id.ind2_val, getColorByValue(data[1].value, color));
	            
	            views.setTextViewText(R.id.ind3_key, data[2].title);
	            views.setTextViewText(R.id.ind3_val, data[2].valueToString());
	            views.setTextColor(R.id.ind3_val, getColorByValue(data[2].value, color));
	            
	            views.setTextViewText(R.id.ind4_key, data[3].title);
	            views.setTextViewText(R.id.ind4_val, data[3].valueToString());
	            views.setTextColor(R.id.ind4_val, getColorByValue(data[3].value, color));

	            views.setTextViewText(R.id.ind5_key, data[4].title);
	            views.setTextViewText(R.id.ind5_val, data[4].valueToString());
	           
	            views.setTextViewText(R.id.ind6_key, data[5].title);
	            views.setTextViewText(R.id.ind6_val, data[5].valueToString());
	            
	            views.setTextViewText(R.id.ind7_key, data[6].title);
	            views.setTextViewText(R.id.ind7_val, data[6].valueToString());
	            
	            
	            Intent i = new Intent(this, Indicators.class);
                i.setAction(REFRESH);
                PendingIntent pi = PendingIntent.getBroadcast(this,0 , i,0);
                views.setOnClickPendingIntent(R.id.indicators_frame, pi);
	            
                
	            
            } catch (Exception e) {
            	Intent i = new Intent(this, Indicators.class);
                i.setAction(ERROR);
                PendingIntent pi = PendingIntent.getBroadcast(this,0 , i,0);
                try {
					pi.send();
				} catch (CanceledException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
            
              widget = new ComponentName(this, Indicators.class);
              manager = AppWidgetManager.getInstance(this);
            manager.updateAppWidget(widget, views);
            
        }

    }
    

}