package io.enwane.app.model;

public class GetSet {
    private static String authToken = null;

    public static String getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(String authToken) {
        GetSet.authToken = authToken;
    }


    public static void reset() {
        GetSet.setAuthToken(null);
    }
}
