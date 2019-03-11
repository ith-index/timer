package com.example.timer

import android.content.SharedPreferences

fun establishSetup(sharedPreferences: SharedPreferences, totalSeconds: Int) {
    writeIsInSetup(sharedPreferences, false)
    writeTotalSeconds(sharedPreferences, totalSeconds)
}

fun clearSetup(sharedPreferences: SharedPreferences) {
    writeIsInSetup(sharedPreferences, true)
    writePreviousElapsedSeconds(sharedPreferences, 0)
    writeIsFinished(sharedPreferences, false)
}

fun play(sharedPreferences: SharedPreferences) {
    writeIsRunning(sharedPreferences, true)
    writeStartTimeMilliseconds(sharedPreferences, System.currentTimeMillis())
}

fun pause(sharedPreferences: SharedPreferences) {
    val previousElapsedSeconds = readPreviousElapsedSeconds(sharedPreferences)
    val startTimeMilliseconds = readStartTimeMilliseconds(sharedPreferences)
    writePreviousElapsedSeconds(sharedPreferences, previousElapsedSeconds + ((System.currentTimeMillis() - startTimeMilliseconds) / 1000).toInt())
    writeIsRunning(sharedPreferences, false)
}

fun reset(sharedPreferences: SharedPreferences) {
    writePreviousElapsedSeconds(sharedPreferences, 0)
    writeIsFinished(sharedPreferences, false)
}

private const val KEY_IS_IN_SETUP = "KEY_IS_IN_SETUP"
private const val KEY_IS_RUNNING = "KEY_IS_RUNNING"
private const val KEY_IS_FINISHED = "KEY_IS_FINISHED"
private const val KEY_TOTAL_SECONDS = "KEY_TOTAL_SECONDS"
private const val KEY_PREVIOUS_ELAPSED_SECONDS = "KEY_PREVIOUS_ELAPSED_SECONDS"
private const val KEY_START_TIME_MILLISECONDS = "KEY_START_TIME_MILLISECONDS"

fun readIsInSetup(sharedPreferences: SharedPreferences): Boolean =
    sharedPreferences.getBoolean(KEY_IS_IN_SETUP, true)

private fun writeIsInSetup(sharedPreferences: SharedPreferences, isInSetup: Boolean) {
    sharedPreferences
        .edit()
        .putBoolean(KEY_IS_IN_SETUP, isInSetup)
        .apply()
}

fun readIsRunning(sharedPreferences: SharedPreferences): Boolean =
    sharedPreferences.getBoolean(KEY_IS_RUNNING, false)

fun writeIsRunning(sharedPreferences: SharedPreferences, isRunning: Boolean) {
    sharedPreferences
        .edit()
        .putBoolean(KEY_IS_RUNNING, isRunning)
        .apply()
}

fun readIsFinished(sharedPreferences: SharedPreferences): Boolean =
    sharedPreferences.getBoolean(KEY_IS_FINISHED, false)

fun writeIsFinished(sharedPreferences: SharedPreferences, isFinished: Boolean) {
    sharedPreferences
        .edit()
        .putBoolean(KEY_IS_FINISHED, isFinished)
        .apply()
}

fun readTotalSeconds(sharedPreferences: SharedPreferences): Int =
    sharedPreferences.getInt(KEY_TOTAL_SECONDS, 1)

fun writeTotalSeconds(sharedPreferences: SharedPreferences, totalSeconds: Int) {
    sharedPreferences
        .edit()
        .putInt(KEY_TOTAL_SECONDS, totalSeconds)
        .apply()
}

fun readPreviousElapsedSeconds(sharedPreferences: SharedPreferences): Int =
    sharedPreferences.getInt(KEY_PREVIOUS_ELAPSED_SECONDS, 0)

fun writePreviousElapsedSeconds(sharedPreferences: SharedPreferences, previousElapsedSeconds: Int) {
    sharedPreferences
        .edit()
        .putInt(KEY_PREVIOUS_ELAPSED_SECONDS, previousElapsedSeconds)
        .apply()
}

fun readStartTimeMilliseconds(sharedPreferences: SharedPreferences): Long =
    sharedPreferences.getLong(KEY_START_TIME_MILLISECONDS, System.currentTimeMillis())

fun writeStartTimeMilliseconds(sharedPreferences: SharedPreferences, startTimeMilliseconds: Long) {
    sharedPreferences
        .edit()
        .putLong(KEY_START_TIME_MILLISECONDS, startTimeMilliseconds)
        .apply()
}