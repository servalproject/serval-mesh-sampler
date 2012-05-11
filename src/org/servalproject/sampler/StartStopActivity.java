package org.servalproject.sampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StartStopActivity extends Activity implements OnClickListener {
	
	/*
	 * private class level constants
	 */
	private String sTag = "MainActivity";
    
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_stop);
        
        // setup the components of the view
        Button mButton = (Button) findViewById(R.id.start_stop_ui_btn_start);
        mButton.setOnClickListener(this);
        
        mButton = (Button) findViewById(R.id.start_stop_ui_btn_stop);
        mButton.setOnClickListener(this);
    }

	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		
		// use an intent to communicate with Serval Mesh
		Intent mIntent;
		
		// determine which button was touched
		switch(v.getId()) {
		case R.id.start_stop_ui_btn_start:
			mIntent = new Intent(getString(R.string.system_serval_mesh_start_action));
			startService(mIntent);
			break;
		case R.id.start_stop_ui_btn_stop:
			mIntent = new Intent(getString(R.string.system_serval_mesh_stop_action));
			startService(mIntent);
			break;
		default:
			Log.d(sTag, "unknown view fired the onClick listener");	
		}
	}

}
