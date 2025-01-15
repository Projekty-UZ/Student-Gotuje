package org.example.uzgotuje.database.entity.recipe;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.uzgotuje.database.entity.auth.User;

/**
 * Entity representing a favorite recipe for a user.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe; // Many-to-One relationship with Recipe

    /**
     * Constructs a new Favorite with the specified user and recipe.
     *
     * @param user the user who favorited the recipe
     * @param recipe the recipe that is favorited
     */
    public Favorite(User user, Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
    }
}