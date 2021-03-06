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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * an activity that demonstrates how to send a file via Rhizome
 */
public class RhizomeAddFileActivity extends Activity implements OnClickListener {
	
	/*
	 * private class level constants
	 */
	private String sTag = "RhizomeAddFileActivity";
	
	private int sRandomBounds = 1000;
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rhizome_add_file);
        
        Button mButton = (Button) findViewById(R.id.rhizome_add_file_ui_btn_share);
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
		case R.id.rhizome_add_file_ui_btn_share:
			shareFileViaRhizome();
			break;
		default:
			Log.d(sTag, "unknown view fired the onClick listener");	
		}
	}
	
	// private method to share a file via Rhizome
	private void shareFileViaRhizome() {
		
		/*
		 *  copy the included file from assets to the SD Card
		 */
		try {
			
			// get the path to the external storage
			String mOutputPath = Environment.getExternalStorageDirectory().getCanonicalPath();
			
			// build a unique file name
			String mOutputFile = String.format(getString(R.string.rhizome_add_file_name), System.currentTimeMillis());
			mOutputFile = mOutputPath + File.separator + mOutputFile;
			
			// open the file
			PrintWriter mWriter = new PrintWriter(new FileWriter(mOutputFile));
			
			// write some content to the file
			mWriter.println(getString(R.string.rhizome_add_file_content_header));
			mWriter.println(String.format(getString(R.string.rhizome_add_file_content_subheader), System.currentTimeMillis())); // ensures that content is always unique
			
			// randomise the size of the file for added testing
			Random mRandomGenerator = new Random(System.currentTimeMillis());
			int mRandom = mRandomGenerator.nextInt(sRandomBounds) + 1;
			
			for(int i = 0; i < mRandom; i++) {
				mWriter.println(getString(R.string.rhizome_add_file_content));
			}
			
			mWriter.close();
			
			// first version of the file (increase by 1 for each new version of the same file)
			String mVersion = "0";
			
			// request that the file be added to rhizome
			Intent mIntent = new Intent(getString(R.string.system_serval_rhizome_add_file_action));
			mIntent.putExtra(getString(R.string.system_serval_rhizome_intent_path_extra), mOutputFile);
			mIntent.putExtra(getString(R.string.system_serval_rhizome_intent_version_extra), mVersion);
			mIntent.putExtra(getString(R.string.system_serval_rhizome_intent_author_extra), getString(R.string.main_ui_lbl_title));
			startService(mIntent);
			
			Toast.makeText(this, String.format(getString(R.string.rhizome_add_file_toast_copy_success), mOutputFile), Toast.LENGTH_LONG).show();
			
			TextView mTextView = (TextView) findViewById(R.id.rhizome_add_file_ui_lbl_file_path);
			mTextView.setText(mOutputFile);
			
		} catch (IOException e) {
			Log.e(sTag, "unable to cret the file on the external storage", e);
			Toast.makeText(this, getString(R.string.rhizome_add_file_toast_copy_fail), Toast.LENGTH_LONG).show();
			return;
		}	
	}
}
