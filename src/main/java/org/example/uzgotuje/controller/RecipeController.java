package org.example.uzgotuje.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.uzgotuje.database.entity.recipe.*;
import org.example.uzgotuje.services.recipe.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * REST controller for handling recipe-related requests.
 */
@RestController
@RequestMapping(path = "/recipe")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class RecipeController {
    private final RecipeService recipeService;

    /**
     * Creates a new ingredient.
     *
     * @param request the request containing ingredient details
     * @param cookieValue the session cookie value
     * @return a response entity with the result and HTTP status
     */
    @PostMapping(path = "/create/ingredient")
    public ResponseEntity<String> createIngredient(@RequestBody CreateIngredientRequest request, @CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        String response = recipeService.createIngredient(request, cookieValue);

        if ("Success".equals(response)) {
            return ResponseEntity.ok("Ingredient created");
        } else if ("Unauthorized".equals(response)) {
            return ResponseEntity.status(401).body(response);
        }
        else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Creates a new tag.
     *
     * @param request the request containing tag details
     * @param cookieValue the session cookie value
     * @return a response entity with the result and HTTP status
     */
    @PostMapping(path = "/create/tag")
    public ResponseEntity<String> createTag(@RequestBody CreateTagRequest request, @CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        String response = recipeService.createTag(request, cookieValue);

        if ("Success".equals(response)) {
            return ResponseEntity.ok("Tag created");
        } else if ("Unauthorized".equals(response)) {
            return ResponseEntity.status(401).body(response);
        }
        else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Creates a new recipe.
     *
     * @param name the name of the recipe
     * @param description the description of the recipe
     * @param type the type of the recipe
     * @param jsonTags the JSON string of tags
     * @param jsonIngredients the JSON string of ingredients
     * @param images the images of the recipe
     * @param cookieValue the session cookie value
     * @return a response entity with the result and HTTP status
     */
    @PostMapping(path = "/create/recipe", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createRecipe(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("type") String type,
            @RequestParam("tags") String jsonTags,
            @RequestParam("ingredients") String jsonIngredients,
            @RequestParam("images") MultipartFile[] images,
            @CookieValue(value = "SESSION_ID", required = false) String cookieValue) {

        //parse tags and ingredients
        List<Tag> tags = parseTags(jsonTags);
        List<RecipeIngredient> ingredients = parseIngredients(jsonIngredients);

        CreateRecipeRequest request = new CreateRecipeRequest(name, description, type, images, tags.toArray(new Tag[0]), ingredients.toArray(new RecipeIngredient[0]));
        String response = recipeService.createRecipe(request, cookieValue);

        if ("Success".equals(response)) {
            return ResponseEntity.ok("Recipe created");
        } else if ("Unauthorized".equals(response)) {
            return ResponseEntity.status(401).body(response);
        }
        else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Parses the JSON string of tags.
     *
     * @param json the JSON string of tags
     * @return a list of parsed tags
     */
    private List<Tag> parseTags(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<>(){});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing tags JSON", e);
        }
    }

    /**
     * Parses the JSON string of ingredients.
     *
     * @param json the JSON string of ingredients
     * @return a list of parsed ingredients
     */
    private List<RecipeIngredient> parseIngredients(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<>(){});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing ingredients JSON", e);
        }
    }

    /**
     * Creates or updates a rating for a recipe.
     *
     * @param request the request containing rating details
     * @param cookieValue the session cookie value
     * @return a response entity with the result and HTTP status
     */
    @PostMapping(path = "/create/rating")
    public ResponseEntity<String> createRating(@RequestBody CreateRatingRequest request , @CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        String response = recipeService.addRating(request.getRecipeId(),request.getScore(), cookieValue);

        if ("Success".equals(response)) {
            return ResponseEntity.ok("Rating created");
        } else if ("Unauthorized".equals(response)) {
            return ResponseEntity.status(401).body(response);
        }
        else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Retrieves the user's rating for a recipe.
     *
     * @param recipeId the ID of the recipe
     * @param cookieValue the session cookie value
     * @return a response entity with the rating or an error status
     */
    @GetMapping(path = "/get/userRating")
    public ResponseEntity<Integer> getUserRating(@RequestParam("recipeId") Long recipeId, @CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        Integer response = recipeService.getUserRating(recipeId, cookieValue);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Creates a new favorite recipe.
     *
     * @param request the request containing favorite details
     * @param cookieValue the session cookie value
     * @return a response entity with the result and HTTP status
     */
    @PostMapping(path = "/create/favorite")
    public ResponseEntity<String> createFavorite(@RequestBody CreateFavoriteRequest request, @CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        String response = recipeService.updateFavorite(request.getRecipeId(), cookieValue);

        if ("Success".equals(response)) {
            return ResponseEntity.ok("Favorite created");
        } else if ("Unauthorized".equals(response)) {
            return ResponseEntity.status(401).body(response);
        }
        else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Retrieves the user's favorite recipes.
     *
     * @param cookieValue the session cookie value
     * @return a response entity with the list of favorite recipes or an error status
     */
    @GetMapping(path = "/get/favorites")
    public ResponseEntity<List<Recipe>> getFavoriteRecipes(@CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        List<Recipe> response = recipeService.getFavoriteRecipes(cookieValue);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    /**
     * Retrieves a recipe by its ID.
     *
     * @param id the ID of the recipe
     * @return a response entity with the recipe or an error status
     */
    @GetMapping(path = "/get/recipe")
    public ResponseEntity<Recipe> getRecipe(@RequestParam("id") Long id) {
        Recipe response = recipeService.getRecipe(id);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Retrieves all recipes.
     *
     * @return a response entity with the list of recipes or an error status
     */
    @GetMapping(path = "/get/recipes")
    public ResponseEntity<List<Recipe>> getRecipes() {
        List<Recipe> response = recipeService.getRecipes();

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Searches for recipes based on the given criteria.
     *
     * @param request the search request containing search criteria
     * @return a response entity with the list of recipes or an error status
     */
    @PostMapping(path = "/get/recipeSearch")
    public ResponseEntity<List<Recipe>> getRecipesBySearch(@RequestBody RecipeSearchRequest request) {
        RecipeTypes type = null;
        if(request.getType() != null) {
            try {
                type = RecipeTypes.valueOf(request.getType());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.emptyList());
            }
        }
        if(request.getTags() != null) {
            for (Tag tag : request.getTags()) {
                try{
                    TagTypes.valueOf(tag.getTagType().toString());
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(Collections.emptyList());
                }
            }
        }else{
            request.setTags(new Tag[0]);
        }
        List<Recipe> response = recipeService.searchRecipes(Arrays.asList(request.getTags()), request.getName(), type, request.isSortByRatingDesc());

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Retrieves all recipe types.
     *
     * @return a response entity with the list of recipe types
     */
    @GetMapping(path = "/get/recipeTypes")
    public ResponseEntity<RecipeTypes[]> getRecipeTypes() {
        return ResponseEntity.ok(RecipeTypes.values());
    }

    /**
     * Retrieves all tags.
     *
     * @return a response entity with the list of tags or an error status
     */
    @GetMapping(path = "/get/tags")
    public ResponseEntity<List<Tag>> getTags() {
        List<Tag> response = recipeService.getTags();

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Retrieves all tag types.
     *
     * @return a response entity with the list of tag types
     */
    @GetMapping(path = "/get/tagTypes")
    public ResponseEntity<TagTypes[]> getTagTypes() {
        return ResponseEntity.ok(TagTypes.values());
    }

    /**
     * Retrieves all ingredients.
     *
     * @return a response entity with the list of ingredients or an error status
     */
    @GetMapping(path = "/get/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredients() {
        List<Ingredient> response = recipeService.getIngredients();

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Retrieves all comments for a recipe.
     *
     * @param recipeId the ID of the recipe
     * @return a response entity with the list of comments or an error status
     */
    @GetMapping(path = "/get/commentsOfRecipe")
    public ResponseEntity<List<Comment>> getCommentsOfRecipe(@RequestParam("recipeId") Long recipeId) {
        List<Comment> response = recipeService.getCommentsOfRecipe(recipeId);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    /**
     * Creates a new comment for a recipe.
     *
     * @param request the request containing comment details
     * @param cookieValue the session cookie value
     * @return a response entity with the result and HTTP status
     */
    @PostMapping(path = "/create/comment")
    public ResponseEntity<String> createComment(@RequestBody CreateCommentRequest request, @CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        String response = recipeService.createComment(request.getRecipeId(),request.getContent(), cookieValue);

        if ("Success".equals(response)) {
            return ResponseEntity.ok("Comment created");
        } else if ("Unauthorized".equals(response)) {
            return ResponseEntity.status(401).body(response);
        }
        else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Retrieves random recipes by type.
     *
     * @param type the type of the recipes
     * @return a response entity with the list of random recipes or an error status
     */
    @GetMapping(path = "/get/randomRecipes")
    public ResponseEntity<List<Recipe>> getRandomRecipes(@RequestParam("type") String type) {
        List<Recipe> response = recipeService.getRandomRecipesByType(type);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }
}
