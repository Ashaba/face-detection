/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ash.facedetector.face;

import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import androidx.annotation.NonNull;

import com.ash.facedetector.VisionProcessorBase;
import com.ash.facedetector.preference.PreferenceUtils;
import com.ash.facedetector.util.GraphicOverlay;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import java.util.List;
import java.util.Locale;

/** Face Detector Demo. */
public class FaceDetectorProcessor extends VisionProcessorBase<List<Face>> {

    private static final String TAG = "FaceDetectorProcessor";

    private final FaceDetector detector;

    public FaceDetectorProcessor(Context context) {
        super(context);
        FaceDetectorOptions faceDetectorOptions = PreferenceUtils.getFaceDetectorOptions(context);
        Log.v(MANUAL_TESTING_LOG, "Face detector options: " + faceDetectorOptions);
        detector = FaceDetection.getClient(faceDetectorOptions);
    }

    @Override
    public void stop() {
        super.stop();
        detector.close();
    }

    @Override
    protected Task<List<Face>> detectInImage(InputImage image) {
        return detector.process(image);
    }

    @Override
    protected void onSuccess(@NonNull List<Face> faces) {
        Log.d(TAG, "onSuccess: Face detected successfully");
//        for (Face face : faces) {
//            graphicOverlay.add(new FaceGraphic(graphicOverlay, face));
//            logExtrasForTesting(face);
//        }
        Log.d(TAG, "onSuccess: Faces detected: " + faces.size());
    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Face detection failed " + e);
    }
}
