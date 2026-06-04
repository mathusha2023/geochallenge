package com.geochallenge.core;

import android.content.Context;
import android.content.res.Resources;

import com.geochallenge.R;

/**
 * Reads native-side configuration from {@code res/values/config.xml}.
 */
public final class ConfigProvider {

    private final int checkpointRadiusMeters;
    private final int teamPollIntervalMs;
    private final int locationUpdateIntervalMs;
    private final String apiBaseUrl;

    public ConfigProvider(Context context) {
        Resources res = context.getResources();
        checkpointRadiusMeters = res.getInteger(R.integer.checkpoint_radius_meters);
        teamPollIntervalMs = res.getInteger(R.integer.team_poll_interval_ms);
        locationUpdateIntervalMs = res.getInteger(R.integer.location_update_interval_ms);
        apiBaseUrl = res.getString(R.string.api_base_url);
    }

    public int getCheckpointRadiusMeters() {
        return checkpointRadiusMeters;
    }

    public int getTeamPollIntervalMs() {
        return teamPollIntervalMs;
    }

    public int getLocationUpdateIntervalMs() {
        return locationUpdateIntervalMs;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }
}
