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

import org.servalproject.sampler.receivers.DidSidReceiver;

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
 * an activity that can be used to retrieve the Serval DID and SID
 */
public class ServalDidSidActivity extends Activity implements OnClickListener {
	
	/*
	 * private class level constants
	 */
	private String sTag = "ServalDidSidActivity";
	
	/*
	 * private class level variables
	 */
	private DidSidReceiver receiver = null;
    
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serval_did_sid);
        
        // setup the components of the view
        Button mButton = (Button) findViewById(R.id.serval_did_sid_ui_btn_back);
        mButton.setOnClickListener(this);
        
        // register the broadcast receiver
        registerReceiver();
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
	@Override
	public void onClick(View v) {
		
		// determine which button was touched and take the appropriate action
		switch(v.getId()) {
		case R.id.serval_did_sid_ui_btn_back:
			// the back button was pressed
			finish();
			break;
		default:
			Log.d(sTag, "unknown view fired the onClick listener");
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
		
		// show the did
		TextView mTextView = (TextView) findViewById(R.id.serval_did_sid_ui_txt_did);
		
		if(intent.getStringExtra(getString(R.string.serval_did_sid_intent_did)) != null) {
			mTextView.setText(intent.getStringExtra(getString(R.string.serval_did_sid_intent_did)));
		} else {
			// an error has occured has the required information isn't in the intent
			Log.d(sTag, "missing did in intent from broadcast receiver");
			mTextView.setText(R.string.serval_did_sid_intent_no_data);
		}
		
		// show the sid
		mTextView = (TextView) findViewById(R.id.serval_did_sid_ui_txt_sid);
		
		if(intent.getStringExtra(getString(R.string.serval_did_sid_intent_sid)) != null) {
			mTextView.setText(intent.getStringExtra(getString(R.string.serval_did_sid_intent_sid)));
		} else {
			// an error has occured has the required information isn't in the intent
			Log.d(sTag, "missing sid in intent from broadcast receiver");
			mTextView.setText(R.string.serval_did_sid_intent_no_data);
		}
		
		// stop receiving the broadcast sticky
		unRegisterReceiver();
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
        mBroadcastFilter.addAction(getString(R.string.system_serval_mesh_sid_did_broadcast));
        
        receiver = new DidSidReceiver();
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
	
	@Override
	public void onDestroy() {
		// ensure that the reciever is no longer registered
		unRegisterReceiver();
		super.onDestroy();
	}

}
