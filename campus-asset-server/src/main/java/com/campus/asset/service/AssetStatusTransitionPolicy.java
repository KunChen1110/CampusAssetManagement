package com.campus.asset.service;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class AssetStatusTransitionPolicy {

    public static final String ACTIVE = "ACTIVE";
    public static final String IDLE = "IDLE";
    public static final String MAINTENANCE = "MAINTENANCE";
    public static final String RETIRED = "RETIRED";

    private static final Set<String> KNOWN_STATUS = new HashSet<>(Arrays.asList(ACTIVE, IDLE, MAINTENANCE, RETIRED));

    public boolean canTransit(String fromStatus, String toStatus) {
        if (!KNOWN_STATUS.contains(toStatus)) {
            return false;
        }
        if (fromStatus == null || fromStatus.equals(toStatus)) {
            return true;
        }
        if (RETIRED.equals(fromStatus)) {
            return false;
        }
        if (RETIRED.equals(toStatus)) {
            return true;
        }
        if (IDLE.equals(fromStatus)) {
            return contains(toStatus, ACTIVE, MAINTENANCE);
        }
        if (ACTIVE.equals(fromStatus)) {
            return contains(toStatus, IDLE, MAINTENANCE);
        }
        if (MAINTENANCE.equals(fromStatus)) {
            return contains(toStatus, IDLE);
        }
        return false;
    }

    public Set<String> knownStatus() {
        return Collections.unmodifiableSet(KNOWN_STATUS);
    }

    private boolean contains(String target, String... candidates) {
        for (String candidate : candidates) {
            if (candidate.equals(target)) {
                return true;
            }
        }
        return false;
    }
}
