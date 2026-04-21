package org.example.tdfinalprog3.repository;

import org.example.tdfinalprog3.model.Collectivity;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CollectivityRepository {
    private final Map<String, Collectivity> collectivities = new ConcurrentHashMap<>();

    public Collectivity save(Collectivity collectivity) {
        collectivities.put(collectivity.getId(), collectivity);
        return collectivity;
    }

    public Optional<Collectivity> findById(String id) {
        return Optional.ofNullable(collectivities.get(id));
    }

    public List<Collectivity> findAll() {
        return new ArrayList<>(collectivities.values());
    }

    public boolean existsById(String id) {
        return collectivities.containsKey(id);
    }

    public void deleteById(String id) {
        collectivities.remove(id);
    }
}