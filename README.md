# Android Barcode Scanner Library

__Disclaimer: this is just a proof of concept project. In no way or form it could be used as is in production.__

## Introduction
Since Google ML Kit allowed for offline usage of pregenerated neural networks I got interested in seeing if I can make a simple barcode reading application. For that, I needed a wrapper to abstract ML Kit interface and provide an easy to use API to application.
This is a library wrapper part.

## Interface
DetectInImageFromBytes - pass in image bytes and dimensions.
IsResultAvailable - to know if barcode information found.
ReadResults - read barcode as string.

## Build
Library is built to app\build\outputs\aar\app-*.aar.
ML data and everything needed is already included into aar as a single blob.