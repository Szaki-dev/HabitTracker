package com.szaki.habbittracker.data

import android.content.Context

object AppPreferences {

    private const val PREF_NAME = "spending_prefs"

    private const val KEY_EMAIL = "email"
    private const val KEY_REMEMBER = "remember_me"
    private const val KEY_HABIT_NAME = "habit_name"
    private const val KEY_HABIT_GOAL = "habit_goal"
    private const val KEY_HABIT_DONE = "habit_done"

    private const val KEY_DARK_MODE = "dark_mode"
    private const val KEY_FONT_SCALE = "font_scale"

    // --------- Login ---------

    fun logout(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }

    fun saveLogin(context: Context, email: String, remember: Boolean) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            if (remember) {
                putString(KEY_EMAIL, email)
                putBoolean(KEY_REMEMBER, true)
            } else {
                remove(KEY_EMAIL)
                putBoolean(KEY_REMEMBER, false)
            }
            apply()
        }
    }

    fun loadLogin(context: Context): Pair<String?, Boolean> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val email = prefs.getString(KEY_EMAIL, null)
        val remember = prefs.getBoolean(KEY_REMEMBER, false)
        return email to remember
    }

    fun loadEmailOnly(context: Context): String? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getString(KEY_EMAIL, null)
    }

    // --------- Habit ---------

    fun deleteHabit(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            remove(KEY_HABIT_NAME)
            remove(KEY_HABIT_GOAL)
            remove(KEY_HABIT_DONE)
            apply()
        }
    }

    fun saveHabit(context: Context, habit: HabitData) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString(KEY_HABIT_NAME, habit.name)
            putString(KEY_HABIT_GOAL, habit.goal)
            putBoolean(KEY_HABIT_DONE, habit.done)
            apply()
        }
    }

    fun loadHabit(context: Context): HabitData? {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val name = prefs.getString(KEY_HABIT_NAME, null)
        val goal = prefs.getString(KEY_HABIT_GOAL, null)
        val done = prefs.getBoolean(KEY_HABIT_DONE, false)


        if (name.isNullOrBlank() || goal.isNullOrBlank()) return null

        return HabitData(
            name = name,
            goal = goal,
            done = done
        )
    }

    // --------- Settings ---------

    fun saveSettings(context: Context, settings: SettingsData) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().apply {
            putBoolean(KEY_DARK_MODE, settings.darkMode)
            putFloat(KEY_FONT_SCALE, settings.fontScale)
            apply()
        }
    }

    fun loadSettings(context: Context): SettingsData {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val dark = prefs.getBoolean(KEY_DARK_MODE, false)
        val scale = prefs.getFloat(KEY_FONT_SCALE, 1f)
        return SettingsData(darkMode = dark, fontScale = scale)
    }
}