package by.health.mama.data;


import android.content.SharedPreferences;

public class Profile {

    private static final long DEFAULT_WATER_TIMER = 3_600_000L;
    private static final int DEFAULT_WATER_GOAL = 2_000;

    String userUID = null;

    public String userName = "Noname";
    public int stature = 0;    // sm
    public int lastWeight = 0; // gram
    public int lastWater = 0;  // ml
    public long lastWaterTime = 0L;    // timestamp

    public int goalWeight = 51_000;    // gram
    public int goalWater = DEFAULT_WATER_GOAL;      // ml
    public long waterNotificationTimer = DEFAULT_WATER_TIMER;      // mil.sec

    private static final String PREF_PROFILE_USER_NAME = "PREF_PROFILE_USER_NAME";
    private static final String PREF_PROFILE_STATURE = "PREF_PROFILE_STATURE";
    private static final String PREF_PROFILE_WEIGHT = "PREF_PROFILE_WEIGHT";
    private static final String PREF_PROFILE_WATER = "PREF_PROFILE_WATER";
    private static final String PREF_PROFILE_WATER_TIME = "PREF_PROFILE_WATER_TS";

    private static final String PREF_PROFILE_GOAL_WEIGHT = "PREF_PROFILE_GOAL_WEIGHT";
    private static final String PREF_PROFILE_GOAL_WATER = "PREF_PROFILE_GOAL_WATER";

    private static final String PREF_PROFILE_WATER_NOTIFICATION_TIMER = "PREF_PROFILE_WATER_NOTIFICATION_TIMER";

    public Profile(String userID) {
        this.userUID = userID;
    }

    public boolean load(SharedPreferences profilePref) {

        userName = profilePref.getString(PREF_PROFILE_USER_NAME, ":)");
        stature = profilePref.getInt(PREF_PROFILE_STATURE, 0);

        lastWeight = profilePref.getInt(PREF_PROFILE_WEIGHT, 0);
        goalWeight = profilePref.getInt(PREF_PROFILE_GOAL_WEIGHT, 0);

        lastWater = profilePref.getInt(PREF_PROFILE_WATER, 0);
        lastWaterTime = profilePref.getLong(PREF_PROFILE_WATER_TIME, 0L);
        goalWater = profilePref.getInt(PREF_PROFILE_GOAL_WATER, DEFAULT_WATER_GOAL);
        waterNotificationTimer = profilePref.getLong(PREF_PROFILE_WATER_NOTIFICATION_TIMER, DEFAULT_WATER_TIMER);

        return !":)".equals(userName) && stature > 0;
    }

    public void save(SharedPreferences profilePref) {
        SharedPreferences.Editor editPref = profilePref.edit();
        editPref.putString(PREF_PROFILE_USER_NAME, userName);
        editPref.putInt(PREF_PROFILE_STATURE, stature);
        //editPref.putInt(PREF_PROFILE_WEIGHT, lastWeight);
        editPref.putInt(PREF_PROFILE_GOAL_WEIGHT, goalWeight);
        //editPref.putInt(PREF_PROFILE_WATER, lastWater);
        //editPref.putLong(PREF_PROFILE_WATER_TIME, lastWaterTime);
        editPref.putInt(PREF_PROFILE_GOAL_WATER, goalWater);
        editPref.putLong(PREF_PROFILE_WATER_NOTIFICATION_TIMER, waterNotificationTimer);
        editPref.apply();
    }

    void addWater(SharedPreferences profilePref, int water) {
        if (water <= 0)
            return;
        long ts = System.currentTimeMillis();
        ts = ts - (ts % 86_400_000);
        if (ts > lastWaterTime) {
            lastWaterTime = ts;
            lastWater = water;
        } else {
            lastWater += water;
        }
        saveWater(profilePref);
    }

    void saveWater(SharedPreferences profilePref) {
        SharedPreferences.Editor editPref = profilePref.edit();
        editPref.putInt(PREF_PROFILE_WATER, lastWater);
        editPref.putLong(PREF_PROFILE_WATER_TIME, lastWaterTime);
        editPref.apply();
    }

    void setWheight(SharedPreferences profilePref, int mass) {
        if (mass < 0)
            return;
        lastWeight = mass;
        profilePref.edit().putInt(PREF_PROFILE_WEIGHT, mass).apply();
    }


}
