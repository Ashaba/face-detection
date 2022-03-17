package com.ash.facedetector.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.annotation.StringRes;

import com.ash.facedetector.R;
import com.google.mlkit.vision.face.FaceDetectorOptions;

/** Utility class to retrieve shared preferences. */
public class PreferenceUtils {

    public static FaceDetectorOptions getFaceDetectorOptions(Context context) {
        int landmarkMode =
                getModeTypePreferenceValue(
                        context,
                        R.string.pref_key_live_preview_face_detection_landmark_mode,
                        FaceDetectorOptions.LANDMARK_MODE_NONE);
        int contourMode =
                getModeTypePreferenceValue(
                        context,
                        R.string.pref_key_live_preview_face_detection_contour_mode,
                        FaceDetectorOptions.CONTOUR_MODE_ALL);
        int classificationMode =
                getModeTypePreferenceValue(
                        context,
                        R.string.pref_key_live_preview_face_detection_classification_mode,
                        FaceDetectorOptions.CLASSIFICATION_MODE_NONE);
        int performanceMode =
                getModeTypePreferenceValue(
                        context,
                        R.string.pref_key_live_preview_face_detection_performance_mode,
                        FaceDetectorOptions.PERFORMANCE_MODE_FAST);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        boolean enableFaceTracking =
                sharedPreferences.getBoolean(
                        context.getString(R.string.pref_key_live_preview_face_detection_face_tracking), false);
        float minFaceSize =
                Float.parseFloat(
                        sharedPreferences.getString(
                                context.getString(R.string.pref_key_live_preview_face_detection_min_face_size),
                                "0.1"));

        FaceDetectorOptions.Builder optionsBuilder =
                new FaceDetectorOptions.Builder()
                        .setLandmarkMode(landmarkMode)
                        .setContourMode(contourMode)
                        .setClassificationMode(classificationMode)
                        .setPerformanceMode(performanceMode)
                        .setMinFaceSize(minFaceSize);
        if (enableFaceTracking) {
            optionsBuilder.enableTracking();
        }
        return optionsBuilder.build();
    }

    /**
     * Mode type preference is backed by {@link android.preference.ListPreference} which only support
     * storing its entry value as string type, so we need to retrieve as string and then convert to
     * integer.
     */
    private static int getModeTypePreferenceValue(
            Context context, @StringRes int prefKeyResId, int defaultValue) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String prefKey = context.getString(prefKeyResId);
        return Integer.parseInt(sharedPreferences.getString(prefKey, String.valueOf(defaultValue)));
    }

    public static boolean isCameraLiveViewportEnabled(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String prefKey = context.getString(R.string.pref_key_camera_live_viewport);
        return sharedPreferences.getBoolean(prefKey, false);
    }

    private PreferenceUtils() {}
}
