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
 * main activity for the Serval Mesh Sampler
 */
public class MainActivity extends Activity implements OnClickListener {
	
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
        setContentView(R.layout.main);
        
        // setup the components of the view
        Button mButton = (Button) findViewById(R.id.main_ui_btn_mesh_installed);
        mButton.setOnClickListener(this);
        
        mButton = (Button) findViewById(R.id.main_ui_btn_sid_did);
        mButton.setOnClickListener(this);
        
        mButton = (Button) findViewById(R.id.main_ui_btn_state_change);
        mButton.setOnClickListener(this);
        
        mButton = (Button) findViewById(R.id.main_ui_btn_state_check);
        mButton.setOnClickListener(this);
        
        mButton = (Button) findViewById(R.id.main_ui_btn_start_stop);
        mButton.setOnClickListener(this);
        
        mButton = (Button) findViewById(R.id.main_ui_btn_send_meshms);
        mButton.setOnClickListener(this);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
	@Override
	public void onClick(View v) {
		
		// use an intent to launch the other activities
		Intent mIntent;
		
		// determine which button was touched
		switch(v.getId()) {
		case R.id.main_ui_btn_mesh_installed:
			mIntent = new Intent(this, org.servalproject.sampler.InstalledActivity.class);
			startActivity(mIntent);
			break;
		case R.id.main_ui_btn_sid_did:
			mIntent = new Intent(this, org.servalproject.sampler.DidSidActivity.class);
			startActivity(mIntent);
			break;
		case R.id.main_ui_btn_state_change:
			mIntent = new Intent(this, org.servalproject.sampler.StateChangedActivity.class);
			startActivity(mIntent);
			break;
		case R.id.main_ui_btn_state_check:
			mIntent = new Intent(this, org.servalproject.sampler.StateCheckActivity.class);
			startActivity(mIntent);
			break;
		case R.id.main_ui_btn_start_stop:
			mIntent = new Intent(this, org.servalproject.sampler.StartStopActivity.class);
			startActivity(mIntent);
			break;
		case R.id.main_ui_btn_send_meshms:
			mIntent = new Intent(this, org.servalproject.sampler.SendMeshMS.class);
			startActivity(mIntent);
			break;
		default:
			Log.d(sTag, "unknown view fired the onClick listener");	
		}
	}
}