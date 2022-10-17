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

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class OpenVRApplication extends Lwjgl3Application {

    static OpenVRManager vrManager = null;

    public OpenVRApplication(VRApplicationListener listener, Lwjgl3ApplicationConfiguration config) {
        super(new ApplicationListener() {


            @Override
            public void create() {
                listener.create();
            }

            @Override
            public void resize(int width, int height) {
                listener.resize(width, height);
            }

            @Override
            public void render() {
                listener.render();
                listener.newFrame();
                drawEyes(listener);
                listener.finishFrame();
            }

            @Override
            public void pause() {
                listener.pause();
            }

            @Override
            public void resume() {
                listener.resume();
            }

            @Override
            public void dispose() {
                listener.dispose();
            }
        }, config);

        vrManager = new OpenVRManager();
    }

    private static void drawEyes(VRApplicationListener listener) {
        if (vrManager != null) {
            listener.drawEye(vrManager.getDisplay().getEye(VREye.Type.Left));
            listener.drawEye(vrManager.getDisplay().getEye(VREye.Type.Right));
        }
    }

}
