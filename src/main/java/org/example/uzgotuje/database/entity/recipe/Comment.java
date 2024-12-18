package org.example.uzgotuje.database.entity.recipe;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public Comment(String content, String username) {
        this.content = content;
        this.username = username;
    }
}
