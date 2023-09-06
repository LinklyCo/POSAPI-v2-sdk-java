package com.linkly.pos.sdk.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class PosVendorDetailsTest {

	private static final String POS_NAME = "pos name";
	private static final String POS_VERSION = "1.2";
	
    @Test
    void should_return_posVendorDetails() {
        UUID posId = UUID.randomUUID();
        UUID posVendorId = UUID.randomUUID();
        PosVendorDetails details = new PosVendorDetails(POS_NAME, POS_VERSION, posId, posVendorId);
        assertEquals(details.getPosName(), POS_NAME);
        assertEquals(details.getPosVersion(), POS_VERSION);
        assertEquals(details.getPosId(), posId);
        assertEquals(details.getPosVendorId(), posVendorId);
    }

    @Test
    void should_throwException_emptyPosName() {
        UUID posId = UUID.randomUUID();
        UUID posVendorId = UUID.randomUUID();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new PosVendorDetails("", POS_VERSION, posId, posVendorId);
        });
        assertEquals("posName is required", exception.getMessage());
    }

    @Test
    void should_throwException_emptyPosVersion() {
        UUID posId = UUID.randomUUID();
        UUID posVendorId = UUID.randomUUID();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new PosVendorDetails("test pos", "", posId, posVendorId);
        });
        assertEquals("posVersion is required", exception.getMessage());
    }
}
