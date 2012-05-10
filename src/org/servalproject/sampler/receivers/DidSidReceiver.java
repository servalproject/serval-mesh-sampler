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
package org.servalproject.sampler.receivers;

import org.servalproject.sampler.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * a broadcast receiver that can retrieve the DID and SID that
 * the Serval Mesh software is using
 */
public class DidSidReceiver extends BroadcastReceiver {
	
	/*
	 * private class level constants
	 */
	private final boolean V_LOG = true;
	private final String sTag = "DidSidReceiver";
	
	/*
	 * private class level variables
	 */
	private String did = null;
	private String sid = null;

	/*
	 * (non-Javadoc)
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		
		if(V_LOG) {
			Log.v(sTag, "receiver was called");
		}
		
		// check on the intent action
		if(intent.getAction().equals(context.getString(R.string.serval_did_sid_intent_action)) == false) {
			// wrong intent action, so exit early
			Log.w(sTag, "receiver called with an unknown intent action");
			return;
		}
		
		// extract the did from the intent
		if(intent.getStringExtra("did") != null) {
			// store the did for later
			did = intent.getStringExtra("did");
		} else {
			did = context.getString(R.string.misc_value_missing);
			Log.e(sTag, "unable to retrieve Serval Mesh did (phone number)");
		}
		
		// extract the sid from the intent
		if(intent.getStringExtra("sid") != null ) {
			// store the sid for later
			sid = intent.getStringExtra("sid");
		} else {
			sid = context.getString(R.string.misc_value_missing);
			Log.e(sTag, "unable to retrieve the Serval Mesh sid (subscriber id)");
		}
		
		// for the purposes of the mesh sampler, update the UI with the new values
		if(did != null && sid != null) {
			
			// create a new Intent
			Intent mIntent = new Intent(context, org.servalproject.sampler.DidSidActivity.class);
			
			// add the data that we've received
			mIntent.putExtra(context.getString(R.string.serval_did_sid_intent_did), did);
			mIntent.putExtra(context.getString(R.string.serval_did_sid_intent_sid), sid);
			
			// restart the activity
			context.startActivity(mIntent);
		}
	}
}
