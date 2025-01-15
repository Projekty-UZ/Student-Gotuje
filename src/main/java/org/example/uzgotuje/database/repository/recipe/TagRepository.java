package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Tag entities.
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

    /**
     * Finds a Tag by its name.
     *
     * @param name the name of the Tag
     * @return an Optional containing the found Tag, or empty if not found
     */
    Optional<Tag> findByName(String name);
}
