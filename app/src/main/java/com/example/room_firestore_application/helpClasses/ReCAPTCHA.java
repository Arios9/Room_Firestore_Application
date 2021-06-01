package com.example.room_firestore_application.helpClasses;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.room_firestore_application.MyActivities.MatchActivity;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ReCAPTCHA {

    private static final String SITE_KEY = "6LesqNAaAAAAAB8AcIWxWB1F53X4LxoPW4LF-_a5";
    private static final String SECRET_KEY = "6LesqNAaAAAAAM4ohp6hXSSYJJifGajRRMW81vyD";
    private final RequestQueue queue;
    private final ReCaptchaTrigger reCaptchaTrigger;
    private final Activity activity;


    public ReCAPTCHA(ReCaptchaTrigger reCaptchaTrigger) {
        this.reCaptchaTrigger = reCaptchaTrigger;
        activity = (Activity) reCaptchaTrigger;
        queue = Volley.newRequestQueue(activity);
        verifyGoogleReCAPTCHA();
    }

    private void verifyGoogleReCAPTCHA() {
        SafetyNet.getClient(activity).verifyWithRecaptcha(SITE_KEY)
            .addOnSuccessListener(activity, response -> {
                // Indicates communication with reCAPTCHA service was successful.
                String userResponseToken = response.getTokenResult();
                if (!userResponseToken.isEmpty()) {
                    // Validate the user response token using the
                    handleVerification(response.getTokenResult());
                }
            })
            .addOnFailureListener(activity, e -> {
                if (e instanceof ApiException) {
                    // An error occurred when communicating with the reCAPTCHA service.
                    ApiException apiException = (ApiException) e;
                    int statusCode = apiException.getStatusCode();
                    Log.d("TAG", "Error: " + CommonStatusCodes.getStatusCodeString(statusCode));
                } else {
                    // A different, unknown type of error occurred.
                    Log.d("TAG", "Error: " + e.getMessage());
                }
            });
    }

    private void handleVerification(final String responseToken) {
        // inside handle verification method we are verifying our user with response token.
        // url to send our site key and secret key to below url using POST method.
        String url = "https://www.google.com/recaptcha/api/siteverify";

        // in this we are making a string request and using a post method to pass the data.
        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getBoolean("success")) {
                            Toast.makeText(activity, "User verified with reCAPTCHA", Toast.LENGTH_SHORT).show();
                            // onReCaptchaUserVerified executes if success
                            reCaptchaTrigger.onReCaptchaUserVerified();
                        } else {
                            Toast.makeText(activity, jsonObject.getString("error-codes"), Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception ex) {
                        Log.d("TAG", "JSON exception: " + ex.getMessage());
                    }
                },
                error -> Log.d("TAG", "Error message: " + error.getMessage())) {
            // below is the getParamns method in which we will be passing our response token and secret key to the above url.
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("secret", SECRET_KEY);
                params.put("response", responseToken);
                return params;
            }
        };
        // below line of code is use to set retry policy if the api fails in one try.
        request.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        // at last we are adding our request to queue.
        queue.add(request);
    }
}
