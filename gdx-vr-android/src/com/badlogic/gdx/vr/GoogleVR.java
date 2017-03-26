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
import com.badlogic.gdx.backends.android.GoogleVRAndroidApplication;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.vr.sdk.base.Eye;

public final class GoogleVR {

	private GoogleVR () {
	}

	private static GoogleVRManager manager;

	public static void Init () {
		if (Gdx.app instanceof GoogleVRAndroidApplication)
			manager = new GoogleVRManager();
		else
			throw new GdxRuntimeException("Application must extend GoogleVRAndroidApplication");
	}

	// Platform specific methods

	static final GoogleVREye[] eyes = new GoogleVREye[3];

	public static VREye convertEye (Eye eye) {
		switch (eye.getType()) {

		case Eye.Type.LEFT:
			if (eyes[VREye.Type.Left.index] == null)
				eyes[VREye.Type.Left.index] = new GoogleVREye(eye);
			else
				eyes[VREye.Type.Left.index].setEye(eye);
			return eyes[VREye.Type.Left.index];

		case Eye.Type.RIGHT:
			if (eyes[VREye.Type.Right.index] == null)
				eyes[VREye.Type.Right.index] = new GoogleVREye(eye);
			else
				eyes[VREye.Type.Right.index].setEye(eye);
			return eyes[VREye.Type.Right.index];

		case Eye.Type.MONOCULAR:
			if (eyes[VREye.Type.Monocular.index] == null)
				eyes[VREye.Type.Monocular.index] = new GoogleVREye(eye);
			else
				eyes[VREye.Type.Monocular.index].setEye(eye);
			return eyes[VREye.Type.Monocular.index];

		default:
			return new GoogleVREye(eye);
		}

	};

}
