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

import org.servalproject.meshms.SimpleMeshMS;
import org.servalproject.sampler.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * a broadcast receiver used to receive notification of new MeshMS messages
 */
public class IncomingMeshMsReceiver extends BroadcastReceiver {
	
	/*
	 * private class level constants
	 */
	private final boolean V_LOG = true;
	private final String sTag = "IncomingMeshMsReceiver";

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
		if(intent.getAction().equals(context.getString(R.string.system_serval_mesh_receive_action)) == false) {
			// wrong intent action, so exit early
			Log.w(sTag, "receiver called with an unknown intent action");
			return;
		}
		
		// get the SimpleMeshMS object
		try {
			SimpleMeshMS mMessage = (SimpleMeshMS) intent.getParcelableExtra(context.getString(R.string.system_serval_meshms_extra_name));
			
			// check to make sure a message was sent
			if(mMessage == null) {
				// the extra we expected is missing
				Log.e(sTag, "recever was called without the required bundle");
				return;
			}
			
			// create an intent to call our activity
			Intent mIntent = new Intent(context, org.servalproject.sampler.ReceiveMeshMsActivity.class);
			
			// populate the intent
			mIntent.putExtra("time", context.getString(R.string.receive_meshms_intent_time));
			mIntent.putExtra("from", context.getString(R.string.receive_meshms_intent_from));
			mIntent.putExtra("content", context.getString(R.string.receive_meshms_intent_content));
			context.startActivity(mIntent);
			
		} catch(ClassCastException e) {
			// the extra we expected was of the wrong type
			Log.e(sTag, "receiver was called with an incorrect bundle", e);
			return;
		}
	}
}
