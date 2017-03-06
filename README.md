# Libgdx VR Extension

##### A libgdx extension for VR app deveplopment

This is the skeleton of a VR extension that will allow multiplatform VR app development on Libgdx.


## What's done so far?

Not much.

At this point, all I have done is update the code from yangweigbh's [Libgdx-CardBoard-Extension](https://github.com/yangweigbh/Libgdx-CardBoard-Extension) and include the code from badlogic's [gdx-vr](https://github.com/badlogic/gdx-vr). They're both working fine withing the project, but they don't integrate into a common multiplatform API.


## What's next?
 
The next step is to abstract the API in order to be able to write a VR application once and run it on the multiple backends.


## Platforms status

### Desktop

The basic bindings are there to support OpenVR via the Lwjgl 3.1.2-SNAPSHOT bindings. This means that pretty much every headset under the sun is supported, as long as they provide an OpenVR driver. *(I'm currently using a Galaxy S6 paired with [VRidge](https://riftcat.com/vridge) as a headset, and emulating the Vive controllers with the help of an XBOX controller and FreePie)*

Headset and controllers are supported. 


### Android
 
###### Google VR

The bindings are there to support Google VR (Cardboard and Daydream) as a headset. The Daydream controller is not yet supported.
  
*I do not have a Daydream View (or a supported phone) yet, but I have been able to emulate the controller on my Galaxy S6 with the help of the [Google VR Services Emulator](https://github.com/domination/gvr-services-emulator) and an old Galaxy S3 running the Controller Emulator.*

###### Oculus / Gear VR

No work has been done yet. Once the API has been fleshed out for the desktop and Google VR backends, I plan on adding bindings for the Oculus SDK. 

*I have managed to set-up my phone for developer mode, but will not have an actual Gear VR headset in my hands for a while.*  

###### NOLO

I have backed the [Kickstarter](https://www.kickstarter.com/projects/243372678/nolo-affordable-motion-tracking-for-mobile-and-ste/) for the NOLO VR and hope to be able to integrate their SDK once I receive my unit to have full positional and tracked controllers support on the Android backends. 


### iOS

No work has been done yet.

*As I don't own any iOS device, and no Mac oustide of a Hackintosh VM, I will probably need some help to integrate the Google VR api on this platform.*
 
 
### GWT

No work has been done yet. Once the API has been fleshed out for the desktop and Google VR backends, I plan on adding the bindings for WebVR.

_WebVR is still in it's infancy and not widely supported._