package com.animeabilities.manager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class EntityCleanupManager {
    private final Set<UUID> entities = new HashSet<>();
    public void track(UUID uuid) { entities.add(uuid); }
    public void cleanupAll() { entities.clear(); }
}
