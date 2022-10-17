# Libgdx VR Extension

##### A libgdx extension for VR app deveplopment

This is the skeleton of a VR extension that will allow multiplatform VR app development on Libgdx.

## What's done so far?

The starting point for this project was to update the code from yangweigbh's [Libgdx-CardBoard-Extension](https://github.com/yangweigbh/Libgdx-CardBoard-Extension) and include the code from badlogic's [gdx-vr](https://github.com/badlogic/gdx-vr).

I have also started to abstract the API, pulling a few design ideas from nooone's [gdx-vr](https://github.com/nooone/gdx-vr).

Rendering is working on Android using Google VR (Cardboard and Daydream) and some groundwork has been done with OpenVR. That's pretty much everything for now.

## What's next?
 
This is the tentative timeline
- ~~Get the Android Google VR rendering working within an abstract API.~~
- Add desktop OpenVR rendering.
- Add support for OpenVR controllers.
- Add support for the Daydream controller.
- Move to OpenXR (This might be pushed forward instead of OpenVR as the market is clearly moving toward this)
- Add other backends. (WebXR, Quest, Neo 3, iOS)
- Add support for speciality VR hardware like the NOLO VR controllers.

## Platforms status

### Desktop

Some groundwork has been done to add OpenVR rendering but it's not yet functional.

Controllers have not been handled yet.

### Android
 
###### Google VR

The rendering for Google VR (Cardboard and Daydream) is working.

The Daydream controller is not yet supported.

NOLO controllers are not yet supported.
  

### iOS

No work has been done yet.

*As I don't own any iOS device, and no Mac, I will probably need some assistance to integrate the Google Cardboard api on this platform.*
 
 
### GWT

No work has been done yet. Once the API has been fleshed out for the desktop and Google VR backends, I plan on adding the bindings for WebXR.

### Meta Quest

Once the API has been fleshed out, I will look into integrating Meta's OpenXR Android SDK 

### Pico Neo 3 / Pico 4

Once the API has been fleshed out, I will look into integrating Pico's OpenXR Android SDK 
