package com.dokunmatikekosistem.app.data.notification

import com.dokunmatikekosistem.app.R
import com.dokunmatikekosistem.app.domain.ConnectionState
import org.junit.Assert.assertEquals
import org.junit.Test

class NotificationHelperTest {

    @Test
    fun `titleResFor DISCONNECTED maps to notif_disconnected`() {
        assertEquals(R.string.notif_disconnected, NotificationHelper.titleResFor(ConnectionState.DISCONNECTED))
    }

    @Test
    fun `titleResFor REGISTERING maps to notif_pairing`() {
        assertEquals(R.string.notif_pairing, NotificationHelper.titleResFor(ConnectionState.REGISTERING))
    }

    @Test
    fun `titleResFor REGISTERED maps to notif_ready`() {
        assertEquals(R.string.notif_ready, NotificationHelper.titleResFor(ConnectionState.REGISTERED))
    }

    @Test
    fun `titleResFor CONNECTED maps to notif_connected`() {
        assertEquals(R.string.notif_connected, NotificationHelper.titleResFor(ConnectionState.CONNECTED))
    }

    @Test
    fun `titleResFor ERROR maps to notif_error`() {
        assertEquals(R.string.notif_error, NotificationHelper.titleResFor(ConnectionState.ERROR))
    }

    @Test
    fun `every ConnectionState maps to a distinct string resource`() {
        val mapped = ConnectionState.entries.map { NotificationHelper.titleResFor(it) }
        assertEquals(
            "each ConnectionState must have its own notification string",
            mapped.size,
            mapped.toSet().size
        )
    }
}