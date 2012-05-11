/*
 * Copyright (C) 2012 The Serval Project
 *
 * This file is part of the Serval Mesh Sampler
 *
 * Serval Mesh Sampler is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.servalproject.sampler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * an activity that includes sample code on how to start / stop the Serval Mesh software
 */
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
