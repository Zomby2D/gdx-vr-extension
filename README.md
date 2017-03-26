# Libgdx VR Extension

##### A libgdx extension for VR app deveplopment

This is the skeleton of a VR extension that will allow multiplatform VR app development on Libgdx.

## What's done so far?

The starting point for this project was to update the code from yangweigbh's [Libgdx-CardBoard-Extension](https://github.com/yangweigbh/Libgdx-CardBoard-Extension) and include the code from badlogic's [gdx-vr](https://github.com/badlogic/gdx-vr).

I have also started to abstract the API, pulling a few design ideas from nooone's [gdx-vr](https://github.com/nooone/gdx-vr).


## What's next?
 
This is the tentative timeline
- ~~Get the Android Google VR rendering working within an abstract API.~~
- Add desktop OpenVR rendering.
- Add support for OpenVR controllers.
- Add support for the Daydream controller.
- Add other backends. (WebVR, Gear VR, iOS)
- Add support for VR hardware like the NOLO VR controllers.

## Platforms status

### Desktop

The basic bindings are there to support OpenVR via the Lwjgl 3.1.2-SNAPSHOT bindings. This means that pretty much every headset under the sun is supported, as long as they provide an OpenVR driver. *(I'm currently using a Galaxy S6 paired with [VRidge](https://riftcat.com/vridge) as a headset, and emulating the Vive controllers with the help of an XBOX controller and FreePie)*

Headset and controllers are supported. 


### Android
 
###### Google VR

The rendering for Google VR (Cardboard and Daydream) is working.

The Daydream controller is not yet supported.
  
*I do not have a Daydream View (or a supported phone) yet, but I have been able to emulate the controller on my Galaxy S6 with the help of the [Google VR Services Emulator](https://github.com/domination/gvr-services-emulator) and an old Galaxy S3 running the Controller Emulator.*

###### Oculus / Gear VR

No work has been done yet. Once the API has been fleshed out for the desktop and Google VR backends, I plan on adding bindings for the Oculus SDK. 

*I have managed to set-up my phone for developer mode, but will not have an actual Gear VR headset in my hands for a few weeks.*  

###### NOLO

I have backed the [Kickstarter](https://www.kickstarter.com/projects/243372678/nolo-affordable-motion-tracking-for-mobile-and-ste/) for the NOLO VR and hope to be able to integrate their SDK once I receive my unit to have full positional and tracked controllers support on the Android backends. 


### iOS

No work has been done yet.

*As I don't own any iOS device, and no Mac oustide of a Hackintosh VM, I will probably need some help to integrate the Google VR api on this platform.*
 
 
### GWT

No work has been done yet. Once the API has been fleshed out for the desktop and Google VR backends, I plan on adding the bindings for WebVR.

_WebVR is still in it's infancy and not widely supported._