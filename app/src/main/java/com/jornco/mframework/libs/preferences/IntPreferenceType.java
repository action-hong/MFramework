package com.jornco.mframework.libs.preferences;

/**
 * Created by kkopite on 2018/7/14.
 */

public interface IntPreferenceType {

    /**
     * Get the current value of the preference.
     */
    int get();

    /**
     * Returns whether a value has been explicitly set for the preference.
     */
    boolean isSet();

    /**
     * Set the preference to a value.
     */
    void set(int value);

    /**
     * Delete the currently stored preference.
     */
    void delete();
}
