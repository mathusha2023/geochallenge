package com.geochallenge.bridge;

/**
 * Method channel names and method identifiers shared with the Flutter side.
 */
public final class ChannelConstants {

    public static final String CHANNEL_GEO = "com.geochallenge/geo";
    public static final String CHANNEL_QUEST = "com.geochallenge/quest";
    public static final String CHANNEL_TEAM = "com.geochallenge/team";
    public static final String CHANNEL_AUTH = "com.geochallenge/auth";

    public static final String METHOD_START_TRACKING = "startTracking";
    public static final String METHOD_STOP_TRACKING = "stopTracking";
    public static final String METHOD_GET_LAST_LOCATION = "getLastLocation";
    public static final String METHOD_VALIDATE_CHECKPOINT = "validateCheckpoint";
    public static final String METHOD_SYNC_SESSION = "syncSession";
    public static final String METHOD_STORE_TOKENS = "storeTokens";
    public static final String METHOD_CLEAR_TOKENS = "clearTokens";
    public static final String METHOD_POLL_TEAM_STATUS = "pollTeamStatus";

    private ChannelConstants() {
    }
}
