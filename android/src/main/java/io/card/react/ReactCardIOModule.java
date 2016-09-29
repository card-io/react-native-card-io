package io.card.react;

import android.view.Gravity;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.*;

import android.util.Log;
import android.app.Activity;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class ReactCardIOModule extends ReactContextBaseJavaModule implements ActivityEventListener {

    public static final String TAG = "io.card.react.CardIO";

    private android.widget.Toast mostRecentToast;
    private static final int REQUEST_CARD_SCAN = 10;

    private Callback successCallback, failureCallback;
    private Promise promise;

    public ReactCardIOModule(ReactApplicationContext reactContext) {
      super(reactContext);
      getReactApplicationContext().addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "ReactCardIOModule";
    }

    @ReactMethod
    public void canScan(Promise promise) throws JSONException {
        if (CardIOActivity.canReadCardWithCamera()) {
            // This is where we return if scanning is enabled.
            promise.resolve("Card Scanning is enabled");
        } else {
            promise.reject("Card Scanning is not enabled");
        }
    }

    @ReactMethod
    public void scan(ReadableMap options, Promise promise) {
      this.promise = promise;

      Intent scanIntent = new Intent(getReactApplicationContext(), CardIOActivity.class);
      scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, this.getOptionBoolean(options, "requireExpiry", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, this.getOptionBoolean(options, "requireCVV", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, this.getOptionBoolean(options, "requirePostalCode", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, this.getOptionBoolean(options, "supressManual", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_RESTRICT_POSTAL_CODE_TO_NUMERIC_ONLY, this.getOptionBoolean(options, "restrictPostalCodeToNumericOnly", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, this.getOptionBoolean(options, "keepApplicationTheme", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CARDHOLDER_NAME, this.getOptionBoolean(options, "requireCardholderName", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO, this.getOptionBoolean(options, "useCardIOLogo", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_INSTRUCTIONS, this.getOptionString(options, "scanInstructions", null)); // default: empty
      scanIntent.putExtra(CardIOActivity.EXTRA_NO_CAMERA, this.getOptionBoolean(options, "noCamera", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_EXPIRY, this.getOptionBoolean(options, "scanExpiry", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, this.getOptionString(options, "languageOrLocale", null)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, this.getOptionBoolean(options, "guideColor", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, this.getOptionBoolean(options, "suppressConfirmation", false)); // default: false
      scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, this.getOptionBoolean(options, "hideCardIOLogo", false)); // default: false

      getReactApplicationContext().startActivityForResult(scanIntent, REQUEST_CARD_SCAN, null);
    }
    
    public void onActivityResult(android.app.Activity activity, int requestCode, int resultCode, Intent intent) {
      Log.i("JAY","Jay Activity "+activity+" Result " + requestCode + " result Code " + resultCode);
      onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
      Log.i(TAG,"Activity Result" + requestCode + " result Code " + resultCode);
      if (REQUEST_CARD_SCAN == requestCode) {
            if (resultCode == CardIOActivity.RESULT_CARD_INFO) {
                CreditCard scanResult = null;
                if (intent.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                    scanResult = intent
                            .getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                    //this.callbackContext.success(this.toJSONObject(scanResult));
                    Log.i(TAG,"Scan Result: " + this.toJSONObject(scanResult));
                    this.promise.resolve(this.toJSONObject(scanResult).toString());
                } else {
                  this.promise.reject("card was scanned but no result");
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                this.promise.reject("card scan cancelled");
            } else {
                this.promise.reject(Integer.toString(resultCode));
            }
        }
    }


    private JSONObject toJSONObject(CreditCard card) {
        JSONObject scanCard = new JSONObject();
        try {
            scanCard.put("cardType", card.getCardType());
            scanCard.put("redactedCardNumber", card.getRedactedCardNumber());
            scanCard.put("cardNumber", card.cardNumber);
            scanCard.put("expiryMonth", card.expiryMonth);
            scanCard.put("expiryYear", card.expiryYear);
            scanCard.put("cvv", card.cvv);
            scanCard.put("postalCode", card.postalCode);
        } catch (JSONException e) {
            scanCard = null;
        }

        return scanCard;
    }

    private Boolean getOptionBoolean(ReadableMap options, String key, Boolean defaultValue) {
        if (options != null && options.hasKey(key) && !options.isNull(key)) {
          return options.getBoolean(key);
        }
        return defaultValue;
    }

    private String getOptionString(ReadableMap options, String key, String defaultValue) {
        if (options != null && options.hasKey(key) && !options.isNull(key)) {
          return options.getString(key);
        }
        return defaultValue;
    }
}
