package com.campus.asset.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssetStatusTransitionPolicyTest {

    private final AssetStatusTransitionPolicy policy = new AssetStatusTransitionPolicy();

    @Test
    void allowsCoreCampusAssetLifecycle() {
        assertTrue(policy.canTransit("IDLE", "ACTIVE"));
        assertTrue(policy.canTransit("ACTIVE", "IDLE"));
        assertTrue(policy.canTransit("IDLE", "MAINTENANCE"));
        assertTrue(policy.canTransit("ACTIVE", "MAINTENANCE"));
        assertTrue(policy.canTransit("MAINTENANCE", "IDLE"));
    }

    @Test
    void rejectsRetiredAssetReactivation() {
        assertTrue(policy.canTransit("IDLE", "RETIRED"));
        assertTrue(policy.canTransit("MAINTENANCE", "RETIRED"));
        assertFalse(policy.canTransit("RETIRED", "IDLE"));
        assertFalse(policy.canTransit("RETIRED", "ACTIVE"));
    }
}
