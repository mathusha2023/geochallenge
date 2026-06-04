package com.geochallenge.core;

import com.geochallenge.auth.TokenVault;
import com.geochallenge.geo.LocationTracker;
import com.geochallenge.pedometer.StepCounterBridge;
import com.geochallenge.quest.RunTimerService;
import com.geochallenge.sync.OfflineQueueManager;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * Центральный обработчик вызовов из Dart через MethodChannel.
 */
public final class NativeBridge implements MethodChannel.MethodCallHandler {

    public static final String CHANNEL = "com.geochallenge/native";

    private final LocationTracker locationTracker;
    private final StepCounterBridge stepCounter;
    private final OfflineQueueManager offlineQueue;
    private final RunTimerService runTimer;
    private final TokenVault tokenVault;

    public NativeBridge(
            BinaryMessenger messenger,
            LocationTracker locationTracker,
            StepCounterBridge stepCounter,
            OfflineQueueManager offlineQueue,
            RunTimerService runTimer,
            TokenVault tokenVault
    ) {
        this.locationTracker = locationTracker;
        this.stepCounter = stepCounter;
        this.offlineQueue = offlineQueue;
        this.runTimer = runTimer;
        this.tokenVault = tokenVault;

        new MethodChannel(messenger, CHANNEL).setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        switch (call.method) {
            case "startLocationUpdates":
                locationTracker.startUpdates(result);
                break;
            case "stopLocationUpdates":
                locationTracker.stopUpdates();
                result.success(null);
                break;
            case "resetStepBaseline":
                stepCounter.resetBaseline();
                result.success(null);
                break;
            case "getStepDelta":
                result.success(stepCounter.getDeltaSinceBaseline());
                break;
            case "enqueueOfflineRequest":
                offlineQueue.enqueue(call.arguments);
                result.success(true);
                break;
            case "syncOfflineQueue":
                offlineQueue.syncPending(result);
                break;
            case "startRunTimer":
                runTimer.start((int) call.argument("durationSec"), result);
                break;
            case "storeTokens":
                tokenVault.store(
                        call.argument("accessToken"),
                        call.argument("refreshToken")
                );
                result.success(null);
                break;
            case "clearTokens":
                tokenVault.clear();
                result.success(null);
                break;
            default:
                result.notImplemented();
        }
    }
}
