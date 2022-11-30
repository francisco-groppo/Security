package org.tutorial.store;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(collectionResourceRel = "stores", path = "stores")
public interface StoreRepository extends CrudRepository<Store, UUID> {
}
