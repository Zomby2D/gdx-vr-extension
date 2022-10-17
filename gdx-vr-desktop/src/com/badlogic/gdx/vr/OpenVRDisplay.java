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

class OpenVRDisplay implements VRDisplay {
	OpenVRContext.VRDevice hmd;
	private final OpenVREye[] eyes = new OpenVREye[3];

	OpenVRDisplay (OpenVRContext vrContext) {
		hmd = vrContext.getDeviceByType(OpenVRContext.VRDeviceType.HeadMountedDisplay);
		eyes[VREye.Type.Left.index] = new OpenVREye(OpenVRContext.Eye.Left,vrContext);
		eyes[VREye.Type.Right.index] = new OpenVREye(OpenVRContext.Eye.Right,vrContext);
	}

	public boolean hasOrientation () {
		return true;
	}

	public boolean hasPosition () {
		return true;
	}

	public boolean hasExternalDisplay () {
		return true;
	}

	public boolean isConnected () {
		return hmd.isConnected();
	}

	public boolean isPresenting () {
		return false;
	}

	public String getVendor () {
		return hmd.getStringProperty(OpenVRContext.VRDeviceProperty.ManufacturerName_String);
	}

	public String getModel () {
		return hmd.getStringProperty(OpenVRContext.VRDeviceProperty.ModelNumber_String);
	}

	public VREye getEye (VREye.Type eyeType) {
		return eyes[eyeType.index];
	}

}
