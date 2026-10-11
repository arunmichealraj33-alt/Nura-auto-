package com.nura.autoaccept

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Switch
import android.widget.Toast

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pad = (20 * resources.displayMetrics.density).toInt()
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad, pad, pad)
            gravity = Gravity.CENTER_HORIZONTAL
        }
        layout.addView(TextView(this).apply {
            text = "Nura Auto Accept"
            textSize = 26f
            setPadding(0, 12, 0, 8)
        })
        layout.addView(TextView(this).apply {
            text = "Detects the visible “Accept” text using Android Accessibility. Enable the service below, then turn auto-click on."
            textSize = 16f
            setPadding(0, 8, 0, 20)
        })
        val enabled = Switch(this).apply {
            text = "Enable automatic clicking"
            textSize = 18f
            isChecked = getSharedPreferences("nura", MODE_PRIVATE).getBoolean("enabled", false)
            setOnCheckedChangeListener { _, checked ->
                getSharedPreferences("nura", MODE_PRIVATE).edit().putBoolean("enabled", checked).apply()
                Toast.makeText(this@MainActivity, if (checked) "Auto-click enabled" else "Auto-click paused", Toast.LENGTH_SHORT).show()
            }
        }
        layout.addView(enabled)
        layout.addView(Button(this).apply {
            text = "Open Accessibility Settings"
            setOnClickListener { startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)) }
        }, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        layout.addView(TextView(this).apply {
            text = "\nNote: this works only when the target app exposes the Accept control to Android Accessibility. Some floating cards may not expose text; OCR/screen-capture support would be a separate implementation."
            textSize = 13f
            setPadding(0, 20, 0, 0)
        })
        setContentView(layout)
    }
}
