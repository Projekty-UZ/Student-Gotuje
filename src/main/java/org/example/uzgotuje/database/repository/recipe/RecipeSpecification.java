package org.example.uzgotuje.database.repository.recipe;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.example.uzgotuje.database.entity.recipe.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

public class RecipeSpecification {

    public static Specification<Recipe> hasTags(Set<Tag> tags) {
        return (root, query, criteriaBuilder) -> {
            if (tags == null || tags.isEmpty()) {
                return criteriaBuilder.conjunction();  // No filter for tags
            }

            // Join with the tags association (to access the recipe's tags)
            Join<Recipe, Tag> tagJoin = root.join("tags", JoinType.INNER);

            // We want to ensure that the recipe has exactly the tags we are looking for
            return criteriaBuilder.and(
                    tagJoin.in(tags)  // This checks if the recipe contains the tags in the provided set
            );
        };
    }

    public static Specification<Recipe> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction(); // return no condition if name is null or empty
            }

            // Create a 'like' condition for recipe name
            Predicate recipeNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");

            // Join with RecipeIngredient to search for ingredients that match the name
            Join<Recipe, RecipeIngredient> recipeIngredientJoin = root.join("ingredients", JoinType.LEFT);
            Join<RecipeIngredient, Ingredient> ingredientJoin = recipeIngredientJoin.join("ingredient", JoinType.LEFT);

            // Create a 'like' condition for ingredient name
            Predicate ingredientNamePredicate = criteriaBuilder.like(criteriaBuilder.lower(ingredientJoin.get("name")), "%" + name.toLowerCase() + "%");

            // Combine both conditions (recipe name and ingredient name) using OR
            return criteriaBuilder.or(recipeNamePredicate, ingredientNamePredicate);
        };
    }

    public static Specification<Recipe> hasType(RecipeTypes type) {
        return (root, query, criteriaBuilder) -> {
            if (type == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("type"), type);
        };
    }
}
