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

package com.badlogic.gdx.vr.tests;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.vr.VRApplicationListener;
import com.badlogic.gdx.vr.VRCamera;
import com.badlogic.gdx.vr.VREye;
import com.badlogic.gdx.vr.VRInterface;

public class GdxVrTest extends ApplicationAdapter implements VRApplicationListener {
	private static final String TAG = "GdxVrTest";

	private VRCamera cam;
	private Model model;
	private ModelInstance instance;
	private ModelBatch batch;
	private Environment environment;
	private static final float Z_NEAR = 0.1f;
	private static final float Z_FAR = 1000.0f;
	private static final float CAMERA_Z = 0.01f;

	@Override
	public void create () {
		cam = new VRCamera();
		cam.position.set(0f, 0f, CAMERA_Z);
		cam.lookAt(0, 0, 0);
		cam.near = Z_NEAR;
		cam.far = Z_FAR;

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.GREEN)),
			VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
		instance = new ModelInstance(model);
		instance.transform.translate(0, 0, -50);

		batch = new ModelBatch();

	}

	int a = 0;
	@Override
	public void render () {

	}

	@Override
	public void dispose () {
		batch.dispose();
		model.dispose();
	}

	@Override
	public void onNewFrame () {
		instance.transform.rotate(0, 1, 0, Gdx.graphics.getDeltaTime() * 30);
	}

	@Override
	public void onDrawEye (VREye eye) {

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		cam.setEye(eye);
		cam.update();
		batch.begin(cam);
		batch.render(instance, environment);
		batch.end();
	}

	@Override
	public void onFinishFrame () {

	}

}
