package org.example.uzgotuje.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.uzgotuje.database.entity.recipe.RecipeIngredient;
import org.example.uzgotuje.database.entity.recipe.Tag;
import org.example.uzgotuje.services.recipe.CreateIngredientRequest;
import org.example.uzgotuje.services.recipe.CreateRecipeRequest;
import org.example.uzgotuje.services.recipe.CreateTagRequest;
import org.example.uzgotuje.services.recipe.RecipeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "/recipe")
@AllArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

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

    @PostMapping(path = "/create/recipe", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> createRecipe(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("type") String type,
            @RequestParam("tags") String jsonTags,
            @RequestParam("ingredients") String jsonIngredients,
            @RequestParam("images") MultipartFile[] images,
            @CookieValue(value = "SESSION_ID", required = false) String cookieValue) {


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
}
