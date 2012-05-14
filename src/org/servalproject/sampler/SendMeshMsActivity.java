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

import org.servalproject.meshms.SimpleMeshMS;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * an activity that includes sample code on how to 
 * send a MeshMS message
 */
public class SendMeshMsActivity extends Activity implements OnClickListener {
	
	/*
	 * private class level constants
	 */
	private String sTag = "SendMeshMS";
    
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_meshms);
        
        Button mButton = (Button) findViewById(R.id.send_meshms_ui_btn_send);
        mButton.setOnClickListener(this);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View.OnClickListener#onClick(android.view.View)
     */
	@Override
	public void onClick(View v) {
		
		// determine which button was touched
		switch(v.getId()) {
		case R.id.send_meshms_ui_btn_send:
			// prepare and send the message
			
			TextView mTextView = (TextView) findViewById(R.id.send_meshms_ui_txt_phone_no);
			
			// check to ensure a phone number has been entered
			if(TextUtils.isEmpty(mTextView.getText()) == true) {
				Toast.makeText(this, getString(R.string.send_meshms_toast_no_phone), Toast.LENGTH_LONG).show();
				return;
			}
			
			if(TextUtils.isDigitsOnly(mTextView.getText()) == false) {
				Toast.makeText(this, getString(R.string.send_meshms_toast_phone_not_digits), Toast.LENGTH_LONG).show();
				return;
			}
			
			// store the phone number for later
			String mPhoneNumber = mTextView.getText().toString();
			
			mTextView = (TextView) findViewById(R.id.send_meshms_ui_txt_content);
			
			// check to ensure that content has been entered
			if(TextUtils.isEmpty(mTextView.getText()) == true) {
				Toast.makeText(this, getString(R.string.send_meshms_toast_no_content), Toast.LENGTH_LONG).show();
				return;
			}
			
			if(mTextView.getText().length() > SimpleMeshMS.MAX_CONTENT_LENGTH) {
				Toast.makeText(this, String.format(getString(R.string.send_meshms_toast_content_length), SimpleMeshMS.MAX_CONTENT_LENGTH), Toast.LENGTH_LONG).show();
				return;
			}
			
			String mContent = mTextView.getText().toString();
			
			// create a new SimpleMeshMS object
			SimpleMeshMS mMessage = new SimpleMeshMS(mPhoneNumber, mContent);

			// send the message
			Intent mMeshMSIntent = new Intent(getString(R.string.system_serval_meshms_send_action));
			mMeshMSIntent.putExtra(getString(R.string.system_serval_meshms_extra_name), mMessage);
			startService(mMeshMSIntent);
			
			// let the user know what happened
			Toast.makeText(this, getString(R.string.send_meshms_toast_sent), Toast.LENGTH_LONG).show();
			
			break;
		default:
			Log.d(sTag, "unknown view fired the onClick listener");	
		}
	}
}
