# card.io React Native Plugin

This plug-in exposes card.io credit card scanning.

Note: If you would like to actually process a credit card charge, you might be interested in the [PayPal Cordova Plug-in](https://github.com/paypal/PayPal-Cordova-Plugin).

## Maintenance of this repository
------------------------------

If you discover a problem here, please submit an Issue or Pull Request. (Unless, of course, the problem is actually in the underlying card.io SDK for either [iOS](https://github.com/card-io/card.io-iOS-SDK) or [Android](https://github.com/card-io/card.io-Android-SDK). We're always interested in discovering and fixing bugs in our SDKs!)

## Supported configurations
------------------------

The card.io Cordova plugin provides different configurations that could be set according to your requirements. Here are the list of supported configurations.

|  **Configuration**               | **Type** | **Description** |
|  :------                         | :------  | :------         |
|  requireExpiry                   | Boolean  | Expiry information will not be required. |
|  requireCVV                      | Boolean  | The user will be prompted for the card CVV |
|  requirePostalCode               | Boolean  | The user will be prompted for the card billing postal code. |
|  supressManual                   | Boolean  | Removes the keyboard button from the scan screen. |
|  restrictPostalCodeToNumericOnly | Boolean  | The postal code will only collect numeric input. Set this if you know the expected country's postal code has only numeric postal codes. |
|  keepApplicationTheme            | Boolean  | The theme for the card.io Activity's will be set to the theme of the application. |
|  requireCardholderName           | Boolean  | The user will be prompted for the cardholder name |
|  scanInstructions                | String   | Used to display instructions to the user while they are scanning their card. |
|  noCamera                        | Boolean  | If set, the card will not be scanned with the camera. |
|  scanExpiry                      | Boolean  | If scanExpiry is true, an attempt to extract the expiry from the card image will be made. |
|  languageOrLocale                | String   | The preferred language for all strings appearing in the user interface. If not set, or if set to null, defaults to the device's current language setting. |
|  guideColor                      | String   | Changes the color of the guide overlay on the camera. The color is provided in hexadecimal format (e.g. "#FFFFFF") |
|  suppressConfirmation            | Boolean  | The user will not be prompted to confirm their card number after processing. |
|  hideCardIOLogo                  | Boolean  | The card.io logo will not be shown overlaid on the camera. |
|  useCardIOLogo                   | Boolean  | The card.io logo will be shown instead of the PayPal logo. |
|  suppressScan                    | Boolean  | Once a card image has been captured but before it has been processed, this value will determine whether to continue processing as usual. |

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
