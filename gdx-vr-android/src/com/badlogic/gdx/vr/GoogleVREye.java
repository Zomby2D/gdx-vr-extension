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

import com.badlogic.gdx.math.Matrix4;
import com.google.vr.sdk.base.Eye;

class GoogleVREye implements VREye {
	private Eye eye;

	GoogleVREye (Eye eye) {
		setEye(eye);
	}

	void setEye (Eye eye) {
		this.eye = eye;
	}

	public VREye.Type getType () {
		switch (eye.getType()) {
		case Eye.Type.LEFT:
			return VREye.Type.Left;
		case Eye.Type.RIGHT:
			return VREye.Type.Right;
		case Eye.Type.MONOCULAR:
			return Type.Monocular;
		}
		return null;
	}

	Matrix4 viewMatrix = new Matrix4();
	public Matrix4 getViewMatrix () {
		viewMatrix.set(eye.getEyeView());
		return viewMatrix;
	}

	Matrix4 projectionMatrix = new Matrix4();
	public Matrix4 getProjectionMatrix (float zNear, float zFar) {
		projectionMatrix.set(eye.getPerspective(zNear, zFar));
		return projectionMatrix;
	}

	public FieldOfView getFieldOfView () {
		return new FieldOfView(eye.getFov().getTop(), eye.getFov().getBottom(), eye.getFov().getLeft(), eye.getFov().getRight());
	}

}
