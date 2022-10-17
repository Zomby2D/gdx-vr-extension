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

import org.lwjgl.openvr.HmdMatrix34;
import org.lwjgl.openvr.HmdMatrix44;
import org.lwjgl.openvr.VRSystem;

class OpenVREye implements VREye {
    private OpenVRContext.Eye eye;
    private FieldOfView fieldOfView;

    OpenVREye (OpenVRContext.Eye eye, OpenVRContext vrContext) {
        this.eye = eye;
        OpenVRContext.VRDevice hmd = vrContext.getDeviceByType(OpenVRContext.VRDeviceType.HeadMountedDisplay);
        fieldOfView = new FieldOfView( hmd.getFloatProperty((OpenVRContext.VRDeviceProperty.FieldOfViewTopDegrees_Float)),
                hmd.getFloatProperty((OpenVRContext.VRDeviceProperty.FieldOfViewBottomDegrees_Float)),
                hmd.getFloatProperty((OpenVRContext.VRDeviceProperty.FieldOfViewLeftDegrees_Float)),
                hmd.getFloatProperty((OpenVRContext.VRDeviceProperty.FieldOfViewRightDegrees_Float)));
    }

    void setEye (OpenVRContext.Eye eye) {
        this.eye = eye;
    }

    public VREye.Type getType (){
        switch (eye) {
            case Left:
                return VREye.Type.Left;
            case Right:
                return VREye.Type.Right;
        }
        return null;
    }

    HmdMatrix34 eyeMat = HmdMatrix34.create();
    Matrix4 eyeSpace = new Matrix4();
    public Matrix4 getViewMatrix () {
        VRSystem.VRSystem_GetEyeToHeadTransform(eye.index, eyeMat);
        OpenVRContext.hmdMat34ToMatrix4(eyeMat, eyeSpace);
        return eyeSpace.inv();
    }

    HmdMatrix44 projectionMat = HmdMatrix44.create();
    Matrix4 projectionMatrix = new Matrix4();
    public Matrix4 getProjectionMatrix (float zNear, float zFar) {
        VRSystem.VRSystem_GetProjectionMatrix(eye.index, zNear, zFar, projectionMat);
        OpenVRContext.hmdMat4toMatrix4(projectionMat, projectionMatrix);
        return projectionMatrix;
    }

    public FieldOfView getFieldOfView () {
        return fieldOfView;
    }

}
