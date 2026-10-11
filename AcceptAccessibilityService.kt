package com.nura.autoaccept

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import android.os.SystemClock
import android.util.Log

class AcceptAccessibilityService : AccessibilityService() {
    private var lastClickAt = 0L
    private val cooldownMs = 1800L

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return
        val prefs = getSharedPreferences("nura", MODE_PRIVATE)
        if (!prefs.getBoolean("enabled", false)) return

        val now = SystemClock.elapsedRealtime()
        if (now - lastClickAt < cooldownMs) return

        val root = rootInActiveWindow ?: return
        try {
            if (clickAcceptNode(root)) {
                lastClickAt = now
                Log.i("NuraAutoAccept", "Clicked visible Accept control")
            }
        } finally {
            root.recycle()
        }
    }

    private fun clickAcceptNode(node: AccessibilityNodeInfo): Boolean {
        val label = node.text?.toString()?.trim()
            ?: node.contentDescription?.toString()?.trim()
            ?: ""

        if (label.equals("Accept", ignoreCase = true) && node.isVisibleToUser && node.isEnabled) {
            if (node.isClickable && node.performAction(AccessibilityNodeInfo.ACTION_CLICK)) return true

            var parent = node.parent
            var depth = 0
            while (parent != null && depth < 5) {
                if (parent.isVisibleToUser && parent.isEnabled && parent.isClickable) {
                    val clicked = parent.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                    parent.recycle()
                    if (clicked) return true
                }
                val next = parent.parent
                parent.recycle()
                parent = next
                depth++
            }
        }

        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val found = clickAcceptNode(child)
            child.recycle()
            if (found) return true
        }
        return false
    }

    override fun onInterrupt() {
        // Android may interrupt accessibility events; no action required.
    }
}
