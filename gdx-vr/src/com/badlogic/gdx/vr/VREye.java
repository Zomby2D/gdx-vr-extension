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

public interface VREye {

	public static enum Type {
		Left(0), Right(1), Monocular(2);

		final int index;

		Type (int index) {
			this.index = index;
		}
	}

	public class FieldOfView {
		public final float Top;
		public final float Bottom;
		public final float Left;
		public final float Right;

		FieldOfView (float top, float bottom, float left, float right) {
			this.Top = top;
			this.Bottom = bottom;
			this.Left = left;
			this.Right = right;
		}
	}

	VREye.Type getType ();

	Matrix4 getViewMatrix ();

	Matrix4 getProjectionMatrix (float zNear, float zFar);

	FieldOfView getFieldOfView ();

}
