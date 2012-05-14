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

import org.servalproject.sampler.receivers.IncomingMeshMsReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * an activity the provides sample code on how to receives a new MeshMS from the 
 * Serval Mesh software
 */
public class ReceiveMeshMsActivity extends Activity implements OnClickListener {
	
	/*
	 * private class level constants
	 */
	private String sTag = "SendMeshMsActivity";
	
	/*
	 * private class level variables
	 */
	private Button startButton;
	private Button stopButton;
	
	private IncomingMeshMsReceiver receiver = null;
    
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_meshms);
        
        // setup the components of the view
        startButton = (Button) findViewById(R.id.receive_meshms_ui_btn_start);
        startButton.setOnClickListener(this);
        
        stopButton = (Button) findViewById(R.id.receive_meshms_ui_btn_stop);
        stopButton.setOnClickListener(this);
        stopButton.setEnabled(false);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
	@Override
	public void onClick(View v) {
		
		// determine which button was touched
		switch(v.getId()) {
		case R.id.receive_meshms_ui_btn_start:
			// start button was pressed
			stopButton.setEnabled(true);
			startButton.setEnabled(false);
			registerReceiver();
			break;
		case R.id.receive_meshms_ui_btn_stop:
			// stop button was pressed
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			unRegisterReceiver();
			break;
		default:
			Log.d(sTag, "unknown view fired the onClick listener");	
		}
		
	}
	
	/*
	 * private method to register the broadcast receiver
	 */
	private void registerReceiver() {
		
		// make sure there isn't a previously registered instance
		if(receiver != null) {
			unRegisterReceiver();
		}
		
        // register the receiver for updates
        IntentFilter mBroadcastFilter = new IntentFilter();
        mBroadcastFilter.addAction(getString(R.string.system_serval_mesh_receive_action));
        
        receiver = new IncomingMeshMsReceiver();
        registerReceiver(receiver, mBroadcastFilter); 
	}
	
	/*
	 * private method to unregister the broadcast receiver
	 */
	private void unRegisterReceiver() {
		if(receiver != null) {
			unregisterReceiver(receiver);
			receiver = null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onNewIntent(android.content.Intent)
	 */
	@Override
	public void onNewIntent(Intent intent) {
		
		/*
		 * onNewIntent is called when the broadcast receiver sends its intent
		 */
		
		// populate the activity with the new information
		TextView mTextView = (TextView) findViewById(R.id.receive_meshms_ui_txt_time);
		mTextView.setText(intent.getStringExtra(getString(R.string.receive_meshms_intent_time)));
		
		mTextView = (TextView) findViewById(R.id.receive_meshms_ui_txt_from);
		mTextView.setText(intent.getStringExtra(getString(R.string.receive_meshms_intent_from)));
		
		mTextView = (TextView) findViewById(R.id.receive_meshms_ui_txt_content);
		mTextView.setText(intent.getStringExtra(getString(R.string.receive_meshms_intent_content)));
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	public void onDestroy() {
		// ensure that the receiver is no longer registered
		unRegisterReceiver();
		super.onDestroy();
	}


}
