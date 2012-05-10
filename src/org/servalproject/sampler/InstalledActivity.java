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
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * sample code to show how to check if the Serval Mesh software
 * is installed
 */
public class InstalledActivity extends Activity implements OnClickListener {
	
	/*
	 * private class level constants
	 */
	private String sTag = "ServalInstalled";
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serval_installed);
        
        // setup the components of the view
        Button mButton = (Button) findViewById(R.id.serval_installed_ui_btn_back);
        mButton.setOnClickListener(this);
        
        ImageView mServalLogo = (ImageView) findViewById(R.id.serval_installed_ui_img_serval_cat);
        
        TextView mInstalledStatus = (TextView) findViewById(R.id.serval_installed_ui_lbl_status);
        
        
        // check to see if the Serval Mesh software is installed
        if(isInstalled() == true) {
        	// the serval mesh software is installed
        	mInstalledStatus.setText(R.string.serval_installed_ui_lbl_status_yes);
        } else {
        	// the serval mesh software is not installed
        	mInstalledStatus.setText(R.string.serval_installed_ui_lbl_status_no);
        	mServalLogo.setVisibility(View.INVISIBLE);
        }
    }
    
    /**
     * a private function to check if the Serval Mesh software is installed on a phone
     */
    private boolean isInstalled() {
    	/*
    	 * use the package manager class to get application information about a package
    	 * identified by the package name used by serval mesh
    	 */
    	try {
    		/*
    		 * if it succeeds the Serval Mesh software is installed
    		 */
			getPackageManager().getApplicationInfo(getString(R.string.system_serval_mesh_package_name), PackageManager.GET_META_DATA);
			return true;
		} catch (NameNotFoundException e) {
			/*
			 * if it fails with this error the Serval Mesh software is not installed
			 */
			return false;
		}
    }

    /*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		
		// determine which button was touched and take the appropriate action
		switch(v.getId()) {
		case R.id.serval_installed_ui_btn_back:
			// the back button was pressed
			finish();
			break;
		default:
			Log.d(sTag, "unknown view fired the onClick listener");
		}
	}
}
