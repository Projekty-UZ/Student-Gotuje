package org.example.uzgotuje.database.entity.recipe;


import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.uzgotuje.database.entity.auth.User;

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

    public Favorite(User user, Recipe recipe) {
        this.user = user;
        this.recipe = recipe;
    }
}
