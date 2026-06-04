package com.geochallenge;

import android.content.Context;

import com.geochallenge.auth.TokenVault;
import com.geochallenge.core.MethodChannelRegistry;
import com.geochallenge.core.NativeBridge;
import com.geochallenge.geo.LocationTracker;
import com.geochallenge.pedometer.StepCounterBridge;
import com.geochallenge.quest.RunTimerService;
import com.geochallenge.sync.OfflineQueueManager;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;

/**
 * Точка входа Flutter-плагина: регистрирует все нативные каналы ГеоВызова.
 */
public final class GeoChallengeNativePlugin implements FlutterPlugin {

    private NativeBridge bridge;
    private LocationTracker locationTracker;
    private StepCounterBridge stepCounter;
    private OfflineQueueManager offlineQueue;
    private RunTimerService runTimer;

    @Override
    public void onAttachedToEngine(FlutterPluginBinding binding) {
        Context context = binding.getApplicationContext();
        BinaryMessenger messenger = binding.getBinaryMessenger();

        TokenVault tokenVault = new TokenVault(context);
        locationTracker = new LocationTracker(context);
        stepCounter = new StepCounterBridge(context);
        offlineQueue = new OfflineQueueManager(context, tokenVault);
        runTimer = new RunTimerService();

        bridge = new NativeBridge(
                messenger,
                locationTracker,
                stepCounter,
                offlineQueue,
                runTimer,
                tokenVault
        );

        MethodChannelRegistry.registerAll(messenger, bridge);
    }

    @Override
    public void onDetachedFromEngine(FlutterPluginBinding binding) {
        if (locationTracker != null) {
            locationTracker.shutdown();
        }
        if (stepCounter != null) {
            stepCounter.shutdown();
        }
        if (offlineQueue != null) {
            offlineQueue.flush();
        }
        if (runTimer != null) {
            runTimer.cancel();
        }
        bridge = null;
    }
}
