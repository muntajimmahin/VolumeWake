package com.example.volumewake

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.PowerManager
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class WakeAccessibilityService : AccessibilityService() {

    private lateinit var powerManager: PowerManager

    override fun onServiceConnected() {
        super.onServiceConnected()
        powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
    }

    // Required override, we don't need it for this task
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    // This is the important part: catches hardware key events
    // even when the screen is OFF, because AccessibilityService
    // with flagRequestFilterKeyEvents runs independent of screen state.
    override fun onKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_VOLUME_DOWN &&
            event.action == KeyEvent.ACTION_DOWN
        ) {
            if (!powerManager.isInteractive) {
                wakeUpScreen()
                // return true = event consumed, so volume won't
                // actually change while screen was off
                return true
            }
        }
        // If screen is already on, let volume key work normally
        return false
    }

    private fun wakeUpScreen() {
        val wakeLock = powerManager.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or
                PowerManager.ACQUIRE_CAUSES_WAKEUP or
                PowerManager.ON_AFTER_RELEASE,
            "VolumeWake:WakeLock"
        )
        wakeLock.acquire(3000L) // 3 second wake, then auto release
        wakeLock.release()
    }
}
