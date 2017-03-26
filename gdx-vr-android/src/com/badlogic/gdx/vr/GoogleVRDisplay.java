/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.vr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationBase;
import com.badlogic.gdx.backends.android.GoogleVRAndroidGraphics;
import com.google.vr.sdk.base.GvrView;
import com.google.vr.sdk.base.HeadMountedDisplay;
import com.google.vr.sdk.base.HeadMountedDisplayManager;

import com.google.vr.sdk.base.Eye;
import com.google.vr.sdk.base.HeadTransform;

class GoogleVRDisplay implements VRDisplay {

	private final HeadMountedDisplayManager hmdManager;
	private final HeadMountedDisplay hmd;
	private final GoogleVREye[] eyes = new GoogleVREye[3];

	GoogleVRDisplay () {
		hmdManager = new HeadMountedDisplayManager(((AndroidApplicationBase)Gdx.app).getContext());
		hmd = hmdManager.getHeadMountedDisplay();
		((GvrView)((GoogleVRAndroidGraphics)Gdx.app.getGraphics()).getView()).getCurrentEyeParams(head, leftEye, rightEye,
				monocularEye, leftEyeNoDistortionCorrection, rightEyeNoDistortionCorrection);
        eyes[VREye.Type.Left.index] = new GoogleVREye(leftEye);
        eyes[VREye.Type.Right.index] = new GoogleVREye(rightEye);
        eyes[VREye.Type.Monocular.index] = new GoogleVREye(monocularEye);
	}

	public boolean hasOrientation () {
		return true;
	}

	public boolean hasPosition () {
		return false;
	}

	public boolean hasExternalDisplay () {
		return false;
	}

	public boolean isConnected () {
		return true;
	}

	public boolean isPresenting () {
		return true;
	}

	public String getVendor () {
		return hmd.getGvrViewerParams().getVendor();
	}

	public String getModel () {
		return hmd.getGvrViewerParams().getModel();
	}

	public VREye getEye (VREye.Type eyeType) {
		return eyes[eyeType.index];
	}

	private HeadTransform head = new HeadTransform();
	private Eye leftEye = new Eye(Eye.Type.LEFT);
	private Eye rightEye = new Eye(Eye.Type.RIGHT);
	private Eye monocularEye = new Eye(Eye.Type.MONOCULAR);
	private Eye leftEyeNoDistortionCorrection = new Eye(Eye.Type.LEFT);
	private Eye rightEyeNoDistortionCorrection = new Eye(Eye.Type.RIGHT);

}
