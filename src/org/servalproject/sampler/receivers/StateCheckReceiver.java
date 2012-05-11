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

public class StateCheckReceiver extends BroadcastReceiver {
	
	/*
	 * private class level constants
	 */
	private final boolean V_LOG = true;
	private final String sTag = "StateCheckReceiver";
	
	/*
	 * a private enum representing the different states of the Serval Mesh software
	 * as taken from the org.serval.project.ServalBatphoneApplication class
	 */
	private enum State{
		Installing(R.string.state_installing),
		Upgrading(R.string.state_upgrading),
		Off(R.string.state_power_off),
		Starting(R.string.state_starting),
		On(R.string.state_power_on),
		Stopping(R.string.state_stopping),
		Broken(R.string.state_broken);

		private int resourceId;

		State(int resourceId) {
			this.resourceId = resourceId;
		}

		public int getResourceId() {
			return resourceId;
		}
	}

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
		if(intent.getAction().equals(context.getString(R.string.system_serval_mesh_state_check_reply)) == false) {
			// wrong intent action, so exit early
			Log.w(sTag, "receiver called with an unknown intent action, '" + intent.getAction() + "'");
			return;
		}
		
		// get the state
		if(intent.getIntExtra("state", -1) != -1) {
			
			// get the state indicator and resolve it to a string
			int mStateIndicator = intent.getIntExtra("state", -1);
			State mState = State.values()[mStateIndicator];
			
			if(V_LOG) {
				Log.v(sTag, "New State: " + context.getString(mState.getResourceId()));
			}
			
			// send the string back to our activity
			Intent mIntent = new Intent(context, org.servalproject.sampler.StateCheckActivity.class);
			mIntent.putExtra(context.getString(R.string.state_check_intent_extra), context.getString(mState.getResourceId()));
			context.startActivity(mIntent);
		} else {
			Log.e(sTag, "State information was missing");
			return;
		}

	}

}
