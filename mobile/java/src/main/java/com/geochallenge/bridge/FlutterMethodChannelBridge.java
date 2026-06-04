package com.geochallenge.bridge;

import android.content.Context;

import com.geochallenge.auth.TokenVault;
import com.geochallenge.core.GeoChallengeException;
import com.geochallenge.core.Result;
import com.geochallenge.geo.LocationTracker;
import com.geochallenge.geo.model.GeoPoint;
import com.geochallenge.quest.CheckpointValidator;
import com.geochallenge.quest.QuestSessionManager;
import com.geochallenge.quest.model.Checkpoint;
import com.geochallenge.team.TeamPresenceTracker;

import java.util.HashMap;
import java.util.Map;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

/**
 * Dispatches Flutter method-channel calls to native services.
 */
public class FlutterMethodChannelBridge implements MethodChannel.MethodCallHandler {

    private final LocationTracker locationTracker;
    private final QuestSessionManager sessionManager;
    private final CheckpointValidator checkpointValidator;
    private final TokenVault tokenVault;
    private final TeamPresenceTracker teamPresenceTracker;

    public FlutterMethodChannelBridge(Context context) {
        locationTracker = new LocationTracker(context, null);
        sessionManager = new QuestSessionManager(context, null);
        checkpointValidator = new CheckpointValidator();
        tokenVault = new TokenVault(context);
        teamPresenceTracker = new TeamPresenceTracker(context);
    }

    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        try {
            switch (call.method) {
                case ChannelConstants.METHOD_START_TRACKING:
                    locationTracker.start();
                    result.success(true);
                    break;
                case ChannelConstants.METHOD_STOP_TRACKING:
                    locationTracker.stop();
                    result.success(true);
                    break;
                case ChannelConstants.METHOD_GET_LAST_LOCATION:
                    result.success(locationToMap(locationTracker.getLastKnownLocation()));
                    break;
                case ChannelConstants.METHOD_VALIDATE_CHECKPOINT:
                    handleValidateCheckpoint(call, result);
                    break;
                case ChannelConstants.METHOD_SYNC_SESSION:
                    sessionManager.syncPending();
                    result.success(true);
                    break;
                case ChannelConstants.METHOD_STORE_TOKENS:
                    tokenVault.store(
                            call.argument("accessToken"),
                            call.argument("refreshToken")
                    );
                    result.success(true);
                    break;
                case ChannelConstants.METHOD_CLEAR_TOKENS:
                    tokenVault.clear();
                    result.success(true);
                    break;
                case ChannelConstants.METHOD_POLL_TEAM_STATUS:
                    result.success(teamPresenceTracker.pollStatus(call.argument("sessionId")));
                    break;
                default:
                    result.notImplemented();
            }
        } catch (GeoChallengeException e) {
            result.error(e.getCode(), e.getMessage(), null);
        }
    }

    private void handleValidateCheckpoint(MethodCall call, MethodChannel.Result result)
            throws GeoChallengeException {
        double lat = call.argument("latitude");
        double lon = call.argument("longitude");
        double checkpointLat = call.argument("checkpointLatitude");
        double checkpointLon = call.argument("checkpointLongitude");
        int radius = call.argument("radiusMeters");

        GeoPoint user = new GeoPoint(lat, lon);
        Checkpoint checkpoint = new Checkpoint(
                call.argument("checkpointId"),
                checkpointLat,
                checkpointLon,
                radius
        );

        Result<Boolean> validation = checkpointValidator.validate(user, checkpoint);
        Map<String, Object> payload = new HashMap<>();
        payload.put("valid", validation.isSuccess() && validation.getValue().orElse(false));
        result.success(payload);
    }

    private Map<String, Object> locationToMap(GeoPoint point) {
        Map<String, Object> map = new HashMap<>();
        if (point != null) {
            map.put("latitude", point.getLatitude());
            map.put("longitude", point.getLongitude());
            map.put("accuracy", point.getAccuracyMeters());
            map.put("timestamp", point.getTimestampMs());
        }
        return map;
    }
}
