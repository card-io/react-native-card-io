# react-native-card-io
Card IO React Native Plugin

## Running Sample App

Make sure you have react-native installed and either an android device or an emulator is connected.
```
npm install -g react-native-cli;
cd SampleApp;
react-native run-android;
```

## Steps done to make Samples work

This is just the documentation of steps taken to create this sample. The same steps could be re-used to integrate card-io react app to your application

```
cd SampleApp;
npm install ../ --save;
```

In `SampleApp/android/settings.gradle`, add following lines:
```
include ':react-native-card-io'
project(':react-native-card-io').projectDir = new File(rootProject.projectDir,   '../node_modules/react-native-card-io/android')
```
We only need the second line as we are doing local build. This may not be necessary once we have an external release.

Now, in `SampleApp/android/app/src/main/java/com/awesomeproject/MainActivity.java`, add `ReactCardIOPackage` to the list:
```
    /**
     * A list of packages used by the app. If the app uses additional views
     * or modules besides the default ones, add more packages here.
     */
    @Override
    protected List<ReactPackage> getPackages() {
        return Arrays.<ReactPackage>asList(
            new MainReactPackage(),
            new ReactCardIOPackage()    // <-- Add ReactCardIOPackage here.
        );
    }
```
