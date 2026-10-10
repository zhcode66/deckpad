package com.dokunmatikekosistem.app.data.bluetooth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AutoConnectTargetSelectionTest {

    private val rememberAddress = "AA:BB:CC:DD:EE:01"
    private val otherAddress = "AA:BB:CC:DD:EE:02"

    @Test
    fun `plugged device wins over remembered address`() {
        assertEquals(
            otherAddress,
            selectAutoConnectAddress(
                pluggedAddress = otherAddress,
                lastDeviceAddress = rememberAddress,
                bondedAddresses = setOf(rememberAddress, otherAddress)
            )
        )
    }

    @Test
    fun `remembered address is used when it is still bonded`() {
        assertEquals(
            rememberAddress,
            selectAutoConnectAddress(
                pluggedAddress = null,
                lastDeviceAddress = rememberAddress,
                bondedAddresses = setOf(rememberAddress, otherAddress)
            )
        )
    }

    @Test
    fun `remembered address that is no longer bonded is ignored`() {
        assertNull(
            selectAutoConnectAddress(
                pluggedAddress = null,
                lastDeviceAddress = rememberAddress,
                bondedAddresses = setOf(otherAddress)
            )
        )
    }

    @Test
    fun `no remembered address yields null instead of an arbitrary bonded device`() {
        assertNull(
            selectAutoConnectAddress(
                pluggedAddress = null,
                lastDeviceAddress = null,
                bondedAddresses = setOf(rememberAddress, otherAddress)
            )
        )
    }

    @Test
    fun `empty bonded set yields null`() {
        assertNull(
            selectAutoConnectAddress(
                pluggedAddress = null,
                lastDeviceAddress = rememberAddress,
                bondedAddresses = emptySet()
            )
        )
    }

    @Test
    fun `plugged device is used even when nothing else is known`() {
        assertEquals(
            otherAddress,
            selectAutoConnectAddress(
                pluggedAddress = otherAddress,
                lastDeviceAddress = null,
                bondedAddresses = emptySet()
            )
        )
    }
}