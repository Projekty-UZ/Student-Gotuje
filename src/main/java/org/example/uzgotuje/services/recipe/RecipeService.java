package org.example.uzgotuje.services.recipe;

import lombok.AllArgsConstructor;
import org.example.uzgotuje.database.entity.auth.UserRoles;
import org.example.uzgotuje.database.entity.recipe.*;
import org.example.uzgotuje.database.repository.recipe.*;
import org.example.uzgotuje.services.authorization.AuthenticationService;
import org.example.uzgotuje.services.fileStorage.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ImageRepository imageRepository;
    private final TagRepository tagRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final AuthenticationService authenticationService;
    private final FileStorageService fileStorageService;

    public String createIngredient(CreateIngredientRequest request, String cookieValue) {
        if(cookieValue == null) {
            return "Unauthorized";
        }
        //check if user is logged in
        if (authenticationService.validateCookieAndGetUser(cookieValue).getAppUserRole() != UserRoles.ADMIN) {
            return "Unauthorized";
        }
        //check if ingredient already exists
        if (ingredientRepository.findByName(request.getName()).isPresent()) {
            return "Bad request";
        }
        Ingredient ingredient = new Ingredient(request.getName());
        ingredientRepository.save(ingredient);
        return "Success";
    }

    public String createTag(CreateTagRequest request, String cookieValue) {
        if(cookieValue == null) {
            return "Unauthorized";
        }
        //check if user is logged in
        if (authenticationService.validateCookieAndGetUser(cookieValue).getAppUserRole() != UserRoles.ADMIN) {
            return "Unauthorized";
        }
        //check if tag already exists
        if (checkTagRequestValidity(request) || tagRepository.findByName(request.getName()).isPresent()) {
            return "Bad request";
        }
        Tag tag = new Tag(request.getTagType(),request.getName());
        tagRepository.save(tag);
        return "Success";
    }

    private boolean checkTagRequestValidity(CreateTagRequest request) {
        if(request.getName() == null || request.getName().isEmpty()) {
            return false;
        }
        if(request.getTagType() == null || request.getTagType().isEmpty()
                || Arrays.stream(TagTypes.values()).anyMatch(tagType -> tagType.name().equals(request.getTagType()))
        ) {
            return false;
        }

        return true;
    }

    public String createRecipe(CreateRecipeRequest request, String cookieValue) {
        if(cookieValue == null) {
            return "Unauthorized";
        }
        //check if user is logged in
        if (authenticationService.validateCookieAndGetUser(cookieValue).getAppUserRole() != UserRoles.ADMIN) {
            return "Unauthorized";
        }
        //check if recipe already exists
        if (!checkCreateRecipeRequestValidity(request) || recipeRepository.findByName(request.getName()).isPresent()) {
            return "Bad request";
        }

        // Create recipe
        Recipe recipe = new Recipe();
        recipe.setName(request.getName());
        recipe.setDescription(request.getDescription());
        recipe.setType(RecipeTypes.valueOf(request.getType()));

        //setTags
        Set<Tag> tags = new HashSet<>(Arrays.asList(request.getTags()));
        recipe.setTags(tags);

        // Set Ingredients
        Set<RecipeIngredient> recipeIngredients = new HashSet<>(Arrays.asList(request.getIngredients()));
        for (RecipeIngredient ingredient : recipeIngredients) {
            ingredient.setRecipe(recipe);
            ingredient.setIngredient(ingredient.getIngredient());
        }
        recipe.setIngredients(recipeIngredients);

        //set Images
        Set<Image> images = new HashSet<>();
        for (MultipartFile file : request.getImages()) {
            String fileName = fileStorageService.saveFile(file);
            Image image = new Image(fileName);
            image.setRecipe(recipe);
            images.add(image);
        }
        recipe.setImages(images);

        recipeRepository.save(recipe);
        return "Success";
    }

    private boolean checkCreateRecipeRequestValidity(CreateRecipeRequest request) {
        if(request.getName() == null || request.getName().isEmpty()) {
            return false;
        }
        if(request.getDescription() == null || request.getDescription().isEmpty()) {
            return false;
        }
        if(request.getType() == null || request.getType().isEmpty()
                || Arrays.stream(RecipeTypes.values()).noneMatch(recipeType -> recipeType.name().equals(request.getType()))
        ){
            return false;
        }
        // Check if each file is a valid image and under 1MB
        for (MultipartFile file : request.getImages()) {
            // Validate file size (<= 1MB)
            if (file.getSize() > 1 * 1024 * 1024) { // 1MB = 1024 * 1024 bytes
                return false;
            }

            // Validate file type
            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                return false;
            }
        }

        // Check if each tag is valid
        for (Tag tag : request.getTags()) {
            Tag databaseTag = tagRepository.findByName(tag.getName()).orElse(null);
            if (databaseTag == null || !databaseTag.getTagType().equals(tag.getTagType()) || databaseTag.getId() != tag.getId()) {
                return false;
            }
        }

        // Check if each ingredient is valid
        for (RecipeIngredient ingredient : request.getIngredients()) {
            Ingredient databaseIngredient = ingredientRepository.findById(ingredient.getIngredient().getId()).orElse(null);
            if (
                    databaseIngredient == null ||
                            !databaseIngredient.getName().equals(ingredient.getIngredient().getName())||
                            ingredient.getQuantity() == null || ingredient.getQuantity().isEmpty() ||
                            ingredient.getQuantityType() == null || ingredient.getQuantityType().isEmpty()){
                return false;
            }
        }

        return true;
    }
}
