package com.geochallenge;

import android.app.Application;

import com.geochallenge.core.ConfigProvider;
import com.geochallenge.geo.LocationTracker;
import com.geochallenge.quest.QuestSessionManager;

/**
 * Application entry point for native Android services used by the Flutter layer.
 */
public class GeoChallengeApplication extends Application {

    private static GeoChallengeApplication instance;

    private ConfigProvider configProvider;
    private LocationTracker locationTracker;
    private QuestSessionManager questSessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        configProvider = new ConfigProvider(this);
        locationTracker = new LocationTracker(this, configProvider);
        questSessionManager = new QuestSessionManager(this, configProvider);
    }

    public static GeoChallengeApplication getInstance() {
        return instance;
    }

    public ConfigProvider getConfigProvider() {
        return configProvider;
    }

    public LocationTracker getLocationTracker() {
        return locationTracker;
    }

    public QuestSessionManager getQuestSessionManager() {
        return questSessionManager;
    }
}
