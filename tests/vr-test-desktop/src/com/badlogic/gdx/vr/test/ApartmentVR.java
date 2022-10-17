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

package com.badlogic.gdx.vr.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.vr.OpenVRCamera;
import com.badlogic.gdx.vr.OpenVRContext;
import com.badlogic.gdx.vr.OpenVRContext.Eye;
import com.badlogic.gdx.vr.OpenVRContext.Space;
import com.badlogic.gdx.vr.OpenVRContext.VRControllerButtons;
import com.badlogic.gdx.vr.OpenVRContext.VRDevice;
import com.badlogic.gdx.vr.OpenVRContext.VRDeviceListener;
import com.badlogic.gdx.vr.OpenVRContext.VRDeviceType;

public class ApartmentVR extends ApplicationAdapter {
	static final String TAG = "ApartmentVR";

	OpenVRContext context;
	PerspectiveCamera companionCamera;
	FirstPersonCameraController cameraController;
	ModelBatch batch;
	ShapeRenderer renderer;
	Environment environment;
	Model discModel;
	ModelInstance disc;
	Array<ModelInstance> modelInstances = new Array<ModelInstance>();
	boolean isTeleporting = false;

	FPSLogger logger = new FPSLogger();

	Plane xzPlane = new Plane(Vector3.Y, 0);
	Ray ray = new Ray();
	Vector3 tmp = new Vector3();
	Vector3 tmp2 = new Vector3();

	public void create () {
		createScene();
		createVR();
	}

	private void createScene () {
		companionCamera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		companionCamera.near = 0.1f;
		companionCamera.far = 1000f;

		batch = new ModelBatch();
		renderer = new ShapeRenderer();

		ModelBuilder modelBuilder = new ModelBuilder();
		discModel = modelBuilder.createCylinder(0.3f, 0.05f, 0.3f, 60, new Material(ColorAttribute.createDiffuse(Color.CORAL)),
			Usage.Position | Usage.Normal);
		disc = new ModelInstance(discModel);

		Pixmap pixmap = new Pixmap(64, 64, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		pixmap.setColor(Color.DARK_GRAY);
		pixmap.fillRectangle(0, 0, 32, 32);
		pixmap.fillRectangle(32, 32, 32, 32);
		Texture texture = new Texture(pixmap);

		Model apartmentModel = new G3dModelLoader(new JsonReader()).loadModel(Gdx.files.internal("apartment.g3dj"));
		ModelInstance apartment = new ModelInstance(apartmentModel);
		apartment.transform.scale(1 / 100f, 1 / 100f, 1 / 100f);
		modelInstances.add(apartment);

		environment = new Environment();
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
	}

	private void createVR () {
		try {
			context = new OpenVRContext();

			context.getEyeData(Eye.Left).camera.far = 100f;
			context.getEyeData(Eye.Right).camera.far = 100f;

			context.addListener(new VRDeviceListener() {
				public void connected (VRDevice device) {
					Gdx.app.log(TAG, device + " connected");
					if (device.getType() == VRDeviceType.Controller && device.getModelInstance() != null)
						modelInstances.add(device.getModelInstance());
				}

				public void disconnected (VRDevice device) {
					Gdx.app.log(TAG, device + " disconnected");
					if (device.getType() == VRDeviceType.Controller && device.getModelInstance() != null)
						modelInstances.removeValue(device.getModelInstance(), true);
				}

				public void buttonPressed (VRDevice device, int button) {
					Gdx.app.log(TAG, device + " button pressed: " + button);

					if (device == context.getDeviceByType(VRDeviceType.Controller)) {
						if (button == VRControllerButtons.SteamVR_Trigger) isTeleporting = true;
					}
				}

				public void buttonReleased (VRDevice device, int button) {
					Gdx.app.log(TAG, device + " button released: " + button);

					if (device == context.getDeviceByType(VRDeviceType.Controller)) {
						if (button == VRControllerButtons.SteamVR_Trigger) {
							if (intersectControllerXZPlane(context.getDeviceByType(VRDeviceType.Controller), tmp)) {
								// Teleportation
								// - Tracker space origin in world space is initially at [0,0,0]
								// - When teleporting, we want to set the tracker space origin in world space to the
								// teleportation point
								// - Then we need to offset the tracker space
								// origin in world space by the camera
								// x/z position so the camera is at the
								// teleportation point in world space
								tmp2.set(context.getDeviceByType(VRDeviceType.HeadMountedDisplay).getPosition(Space.Tracker));
								tmp2.y = 0;
								tmp.sub(tmp2);

								context.getTrackerSpaceOriginToWorldSpaceTranslationOffset().set(tmp);
							}
							isTeleporting = false;
						}
					}
				}
			});
		} catch (Exception e) {
			// If initializing the VRInterface failed, we fall back
			// to desktop only mode with a FirstPersonCameraController.
			cameraController = new FirstPersonCameraController(companionCamera);
			Gdx.input.setInputProcessor(cameraController);

			// Set the camera height to 1.7m to emulate an
			// average human's height. We'd get this from the
			// HMD tracking otherwise.
			companionCamera.position.y = 1.7f;

			// We also enable vsync which the VRInterface would have
			// managed otherwise
			Gdx.graphics.setVSync(true);
		}
	}

	private boolean intersectControllerXZPlane (VRDevice controller, Vector3 intersection) {
		ray.origin.set(controller.getPosition(Space.World));
		ray.direction.set(controller.getDirection(Space.World).nor());
		return Intersector.intersectRayPlane(ray, xzPlane, intersection);
	}

	public void render () {
		logger.log();

		// In case initializing the VRInterface succeeded
		if (context != null) {
			// poll the latest tracking data. must be called
			// before context.begin()!
			context.pollEvents();

			// check if we are teleporting (first controller trigger button is down)
			modelInstances.removeValue(disc, true);
			if (isTeleporting) {
				// Intersect a ray along the controller's pointing direction with the
				// xz plane. If there's an intersection, place the disc at the position
				// and add it to the model instances to be rendered.
				if (intersectControllerXZPlane(context.getDeviceByType(VRDeviceType.Controller), tmp)) {
					disc.transform.idt().translate(tmp);
					modelInstances.add(disc);
				}
			}

			// render the scene for the left/right eye
			context.begin();
			renderScene(Eye.Left);
			renderScene(Eye.Right);
			context.end();

			// Render to the companion window (manually, see VRInterface for
			// helpers)
			VRDevice hmd = context.getDeviceByType(VRDeviceType.HeadMountedDisplay);
			companionCamera.direction.set(hmd.getDirection(Space.World));
			companionCamera.up.set(hmd.getUp(Space.World));
			companionCamera.position.set(hmd.getPosition(Space.World));
			companionCamera.update();
			renderScene(companionCamera);
		} else {
			// In desktop only mode, we just update the camera
			// controller
			cameraController.update();
			renderScene(companionCamera);
		}
	}

	Vector3 position = new Vector3();
	Vector3 xAxis = new Vector3();
	Vector3 yAxis = new Vector3();
	Vector3 zAxis = new Vector3();

	private void renderScene (Eye eye) {
		OpenVRCamera camera = context.getEyeData(eye).camera;
		context.beginEye(eye);
		renderScene(camera);
		context.endEye();
	}

	private void renderScene (Camera camera) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

		// render all the models in the scene
		batch.begin(camera);
		for (ModelInstance modelInstance : modelInstances)
			batch.render(modelInstance, environment);
		batch.end();

		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeType.Line);

		if (isTeleporting) {
			renderer.setColor(Color.CORAL);
			renderer.line(context.getDeviceByType(VRDeviceType.Controller).getPosition(Space.World),
				disc.transform.getTranslation(tmp));
		}

		// render coordinate system axes for orientation
// renderer.setColor(Color.WHITE);
// renderer.line(-100, 0, 0, 0, 0, 0);
// renderer.line(0, -100, 0, 0, 0, 0);
// renderer.line(0, 0, -100, 0, 0, 0);
// renderer.setColor(Color.RED);
// renderer.line(0, 0, 0, 100, 0, 0);
// renderer.setColor(Color.GREEN);
// renderer.line(0, 0, 0, 0, 100, 0);
// renderer.setColor(Color.BLUE);
// renderer.line(0, 0, 0, 0, 0, 100);
		renderer.end();
//
// // render direction, up and right axes of each controller if
// // the VRInterface was successfully created
// if (context != null) {
// renderer.begin(ShapeType.Line);
// for (VRDevice device : context.getDevices()) {
// if (device.getType() == VRDeviceType.Controller) {
// renderer.setColor(Color.BLUE);
// Vector3 pos = tmp.set(device.getPosition(Space.World));
// Vector3 dir = tmp2.set(device.getDirection(Space.World)).scl(0.5f);
// renderer.line(device.getPosition(Space.World), pos.add(dir));
//
// renderer.setColor(Color.GREEN);
// pos = tmp.set(device.getPosition(Space.World));
// dir = tmp2.set(device.getUp(Space.World)).scl(0.1f);
// renderer.line(device.getPosition(Space.World), pos.add(dir));
//
// renderer.setColor(Color.RED);
// pos = tmp.set(device.getPosition(Space.World));
// dir = tmp2.set(device.getRight(Space.World)).scl(0.1f);
// renderer.line(device.getPosition(Space.World), pos.add(dir));
// }
// }
// renderer.end();
// }
	}

	public void dispose () {
		if (context != null) context.dispose();
		batch.dispose();
		renderer.dispose();
		discModel.dispose();
	}

	public static void main (String[] args) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		// Note that we disable VSync! The VRInterface manages vsync with respect
		// to
		// the HMD
		config.useVsync(false);
		config.setWindowedMode(800, 600);
		new Lwjgl3Application(new ApartmentVR(), config);
	}
}
