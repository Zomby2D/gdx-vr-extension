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

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class VRCamera extends Camera {

	private final Matrix4 tmpMatrix = new Matrix4();
	private final Vector3 tmpVec = new Vector3();
	private VREye eye;

	public void setEye (VREye eye) {
		this.eye = eye;
	}

	@Override
	public void update () {
		update(true);
	}

	@Override
	public void update (boolean updateFrustum) {
		view.setToLookAt(position, tmpVec.set(position).add(direction), up);

		tmpMatrix.set(eye.getViewMatrix());
		Matrix4.mul(tmpMatrix.val, view.val);

		this.projection.set(eye.getProjectionMatrix(near, far));
		combined.set(projection);
		Matrix4.mul(combined.val, tmpMatrix.val);

		if (updateFrustum) {
			invProjectionView.set(combined);
			Matrix4.inv(invProjectionView.val);
			frustum.update(invProjectionView);
		}
	}

}
