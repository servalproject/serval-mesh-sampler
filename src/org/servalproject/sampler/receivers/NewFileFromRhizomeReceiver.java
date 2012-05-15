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
 * a broadcast receiver which shows how to receive notification of a new
 * file from Rhizome
 */
public class NewFileFromRhizomeReceiver extends BroadcastReceiver {
	
	/*
	 * private class level constants
	 */
	private final boolean V_LOG = true;
	private final String sTag = "NewFileFromRhizomeReceiver";

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
		if(intent.getAction().equals(context.getString(R.string.system_serval_rhizome_receive_file_action)) == false) {
			// wrong intent action, so exit early
			Log.w(sTag, "receiver called with an unknown intent action");
			return;
		}
		
		// process the new information
		Intent mIntent = new Intent(context, org.servalproject.sampler.RhizomeReceiveFileActivity.class);
		
		// add the values from the intent from rhizome to the new intent
		mIntent.putExtra(
				context.getString(R.string.system_serval_rhizome_intent_path_extra), 
				intent.getStringExtra(context.getString(R.string.system_serval_rhizome_intent_path_extra))
			);
		
		mIntent.putExtra(
				context.getString(R.string.system_serval_rhizome_intent_version_extra), 
				intent.getStringExtra(context.getString(R.string.system_serval_rhizome_intent_version_extra))
			);
		
		mIntent.putExtra(
				context.getString(R.string.system_serval_rhizome_intent_author_extra), 
				intent.getStringExtra(context.getString(R.string.system_serval_rhizome_intent_author_extra))
			);

	}

}
