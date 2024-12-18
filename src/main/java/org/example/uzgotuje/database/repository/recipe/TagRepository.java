package org.example.uzgotuje.database.repository.recipe;

import org.example.uzgotuje.database.entity.recipe.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    //find tag by name
    Optional<Tag> findByName(String name);

}
