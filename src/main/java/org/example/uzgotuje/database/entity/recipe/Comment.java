package org.example.uzgotuje.database.entity.recipe;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a comment on a recipe.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String username;

    @ManyToOne
    @JsonBackReference(value = "recipe-comment")
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    /**
     * Constructs a new Comment with the specified content and username.
     *
     * @param content the content of the comment
     * @param username the username of the commenter
     */
    public Comment(String content, String username) {
        this.content = content;
        this.username = username;
    }
}
