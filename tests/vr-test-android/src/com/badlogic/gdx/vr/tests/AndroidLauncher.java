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

		import android.os.Bundle;

		import com.badlogic.gdx.Gdx;
		import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
		import com.badlogic.gdx.backends.android.GvrAndroidApplication;
		import com.badlogic.gdx.backends.android.GvrApplicationListener;
		import com.badlogic.gdx.vr.GvrCamera;
		import com.badlogic.gdx.graphics.Color;
		import com.badlogic.gdx.graphics.GL20;
		import com.badlogic.gdx.graphics.VertexAttributes.Usage;
		import com.badlogic.gdx.graphics.g3d.Environment;
		import com.badlogic.gdx.graphics.g3d.Material;
		import com.badlogic.gdx.graphics.g3d.Model;
		import com.badlogic.gdx.graphics.g3d.ModelBatch;
		import com.badlogic.gdx.graphics.g3d.ModelInstance;
		import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
		import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
		import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
		import com.badlogic.gdx.math.Matrix4;

		import com.google.vr.sdk.base.Eye;
		import com.google.vr.sdk.base.HeadTransform;
		import com.google.vr.sdk.base.Viewport;

public class AndroidLauncher  extends GvrAndroidApplication implements GvrApplicationListener{

	private GvrCamera cam;
	private Model model;
	private ModelInstance instance;
	private ModelBatch batch;
	private Environment environment;
	private static final float Z_NEAR = 0.1f;
	private static final float Z_FAR = 1000.0f;
	private static final float CAMERA_Z = 0.01f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(/*new GdxVrTest()*/ this, config);
	}

	@Override
	public void create() {
		cam = new GvrCamera();
		cam.position.set(0f, 0f, CAMERA_Z);
		cam.lookAt(0,0,0);
		cam.near = Z_NEAR;
		cam.far = Z_FAR;

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

		ModelBuilder modelBuilder = new ModelBuilder();
		model = modelBuilder.createBox(5f, 5f, 5f,
				new Material(ColorAttribute.createDiffuse(Color.GREEN)),
				Usage.Position | Usage.Normal);
		instance = new ModelInstance(model);
		instance.transform.translate(0, 0, -50);

		batch = new ModelBatch();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		model.dispose();
	}

	@Override
	public void onNewFrame(HeadTransform paramHeadTransform) {
		instance.transform.rotate(0, 1, 0, Gdx.graphics.getDeltaTime() * 30);
	}

	@Override
	public void onDrawEye(Eye eye) {
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		// Apply the eye transformation to the camera.
		cam.setEyeViewAdjustMatrix(new Matrix4(eye.getEyeView()));

		float[] perspective = eye.getPerspective(Z_NEAR, Z_FAR);
		cam.setEyeProjection(new Matrix4(perspective));
		cam.update();

		batch.begin(cam);
		batch.render(instance, environment);
		batch.end();
	}

	@Override
	public void onFinishFrame(Viewport paramViewport) {

	}

	@Override
	public void onRendererShutdown() {

	}

	@Override
	public void onCardboardTrigger() {

	}
}