package com.dokunmatikekosistem.app.data.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import com.dokunmatikekosistem.app.R
import com.dokunmatikekosistem.app.domain.ConnectionState
import java.util.Locale

const val HID_SERVICE_NOTIFICATION_CHANNEL_ID = "hid_service_channel"
const val HID_SERVICE_NOTIFICATION_ID = 1

/**
 * Single source of truth for how each [ConnectionState] is described to the user —
 * both the on-screen "Durum: ..." text and the persistent foreground-service notification
 * read from [titleResFor] so the two never drift apart.
 */
object NotificationHelper {

    @StringRes
    fun titleResFor(state: ConnectionState): Int = when (state) {
        ConnectionState.DISCONNECTED -> R.string.notif_disconnected
        ConnectionState.REGISTERING -> R.string.notif_pairing
        ConnectionState.REGISTERED -> R.string.notif_ready
        ConnectionState.CONNECTED -> R.string.notif_connected
        ConnectionState.ERROR -> R.string.notif_error
    }

    fun ensureChannel(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = context.getSystemService(NotificationManager::class.java)
        val channel = NotificationChannel(
            HID_SERVICE_NOTIFICATION_CHANNEL_ID,
            context.getString(R.string.notif_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        manager.createNotificationChannel(channel)
    }

    fun build(context: Context, state: ConnectionState): Notification =
        NotificationCompat.Builder(context, HID_SERVICE_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(context.getString(titleResFor(state)))
            .setSmallIcon(R.drawable.ic_stat_deckpad)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .build()

    /**
     * Builds the notification with every string resolved in [languageTag] instead of the
     * device language. The service runs outside the Compose tree, so it never sees the
     * `CompositionLocalProvider` override that `MainAppScreen` installs for the in-app
     * UI — without this, the notification would follow the device language while the
     * screen it sits above followed the user's in-app choice, and the two would disagree.
     */
    fun build(context: Context, state: ConnectionState, languageTag: String): Notification {
        val config = Configuration(context.resources.configuration).apply {
            setLocale(Locale.forLanguageTag(languageTag))
        }
        val localizedContext: Context = context.createConfigurationContext(config)
        return build(localizedContext, state)
    }
}
