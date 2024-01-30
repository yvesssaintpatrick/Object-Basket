package com.example.objectbasket;

public class SharedData {
    private static SharedData instance;
    private int selectedDrawableResId;

    private SharedData() {
        // Private constructor to prevent instantiation
    }

    public static synchronized SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public int getSelectedDrawableResId() {
        return selectedDrawableResId;
    }

    public void setSelectedDrawableResId(int selectedDrawableResId) {
        this.selectedDrawableResId = selectedDrawableResId;
    }
}

