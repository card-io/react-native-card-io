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

    public static final String TAG = "com.remobile.toast.CardIO";

    private android.widget.Toast mostRecentToast;
    private static final int REQUEST_CARD_SCAN = 10;

    private ReactApplicationContext reactContext;
    private Callback successCallback, failureCallback;
    private Promise promise;

    public ReactCardIOModule(ReactApplicationContext reactContext) {
      super(reactContext);
      this.reactContext = reactContext;
      this.reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "ReactCardIOModule";
    }

    @ReactMethod
    public void scan(ReadableMap options, Promise promise) {
      this.promise = promise;
      final Boolean requireExpiry = options.getBoolean("requireExpiry");

      Intent scanIntent = new Intent(this.reactContext, CardIOActivity.class);
      this.reactContext.startActivityForResult(scanIntent, REQUEST_CARD_SCAN, null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
      Log.i("JAY","Jay Activity Result" + requestCode + " result Code " + resultCode);
      if (REQUEST_CARD_SCAN == requestCode) {
            if (resultCode == CardIOActivity.RESULT_CARD_INFO) {
                CreditCard scanResult = null;
                if (intent.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                    scanResult = intent
                            .getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);
                    //this.callbackContext.success(this.toJSONObject(scanResult));
                    Log.i("JAY","Scan Result: " + this.toJSONObject(scanResult));
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
}
