package org.example.uzgotuje.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.uzgotuje.database.entity.recipe.*;
import org.example.uzgotuje.services.recipe.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/recipe")
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    //path to create ingredient
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


    //path to create tag
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

    //path to create recipe
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

    private List<Tag> parseTags(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<List<Tag>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing tags JSON", e);
        }
    }

    private List<RecipeIngredient> parseIngredients(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, new TypeReference<List<RecipeIngredient>>(){});
        } catch (Exception e) {
            throw new RuntimeException("Error parsing ingredients JSON", e);
        }
    }

    //path to create / update rating
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

    @GetMapping(path = "/get/favorites")
    public ResponseEntity<List<Recipe>> getFavoriteRecipes(@CookieValue(value = "SESSION_ID", required = false) String cookieValue) {
        List<Recipe> response = recipeService.getFavoriteRecipes(cookieValue);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping(path = "/get/recipe")
    public ResponseEntity<Recipe> getRecipe(@RequestParam("id") Long id) {
        Recipe response = recipeService.getRecipe(id);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping(path = "/get/recipes")
    public ResponseEntity<List<Recipe>> getRecipes() {
        List<Recipe> response = recipeService.getRecipes();

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping(path = "/get/recipeSearch")
    public ResponseEntity<List<Recipe>> getRecipesBySearch(@RequestBody RecipeSearchRequest request) {
        RecipeTypes type = null;
        if(request.getType() != null) {
            type = RecipeTypes.valueOf(request.getType());
        }
        List<Recipe> response = recipeService.searchRecipes(Arrays.asList(request.getTags()), request.getName(), type, request.isSortByRatingDesc());

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping(path = "/get/recipeTypes")
    public ResponseEntity<RecipeTypes[]> getRecipeTypes() {
        return ResponseEntity.ok(RecipeTypes.values());
    }

    @GetMapping(path = "/get/tags")
    public ResponseEntity<List<Tag>> getTags() {
        List<Tag> response = recipeService.getTags();

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping(path = "/get/tagTypes")
    public ResponseEntity<TagTypes[]> getTagTypes() {
        return ResponseEntity.ok(TagTypes.values());
    }

    @GetMapping(path = "/get/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredients() {
        List<Ingredient> response = recipeService.getIngredients();

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(null);
        }
    }

}
