package org.example.uzgotuje.services.recipe;

import lombok.AllArgsConstructor;
import org.example.uzgotuje.database.entity.auth.User;
import org.example.uzgotuje.database.entity.auth.UserRoles;
import org.example.uzgotuje.database.entity.recipe.*;
import org.example.uzgotuje.database.repository.recipe.*;
import org.example.uzgotuje.services.authorization.AuthenticationService;
import org.example.uzgotuje.services.fileStorage.FileStorageService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final TagRepository tagRepository;
    private final IngredientRepository ingredientRepository;
    private final RatingRepository ratingRepository;
    private final FavoriteRepository favoriteRepository;
    private final AuthenticationService authenticationService;
    private final FileStorageService fileStorageService;
    private final CommentRepository commentRepository;

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
        if (!checkTagRequestValidity(request) || tagRepository.findByName(request.getName()).isPresent()) {
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
                || Arrays.stream(TagTypes.values()).noneMatch(tagType -> tagType.name().equals(request.getTagType()))
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

    public Recipe getRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId).orElse(null);
    }

    public List<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> searchRecipes(List<Tag> tags, String name, RecipeTypes type, boolean sortByRatingDesc) {
        Specification<Recipe> spec = Specification.where(RecipeSpecification.hasTags(new HashSet<>(tags)))
                .and(RecipeSpecification.hasName(name))
                .and(RecipeSpecification.hasType(type));

        Sort sort = sortByRatingDesc ? Sort.by("averageRating").descending() : Sort.by("averageRating").ascending();

        return recipeRepository.findAll(spec, sort);
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
            if (file.getSize() > 1024 * 1024) { // 1MB = 1024 * 1024 bytes
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

    public String addRating(Long recipeId, Integer score, String cookieValue) {
        if(cookieValue == null) {
            return "Unauthorized";
        }
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(recipe.isEmpty() || score < 1 || score > 5) {
            return "Bad request";
        }
        User user = authenticationService.validateCookieAndGetUser(cookieValue);
        // if rating already exists, update it
        Rating dbRating = ratingRepository.findByUserAndRecipe(user, recipe.get()).orElse(null);
        if(dbRating != null) {
            dbRating.setScore(score);
            ratingRepository.save(dbRating);
            recipe.get().updateAverageRating();
            recipeRepository.save(recipe.get());
        }else {
            Rating rating = new Rating(user, recipe.get(), score);
            ratingRepository.save(rating);
            recipe.get().updateAverageRating();
            recipeRepository.save(recipe.get());
        }
        return "Success";
    }

    public Integer getUserRating(Long recipeId, String cookieValue) {
        if(cookieValue == null) {
            return 0;
        }
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(recipe.isEmpty()) {
            return 0;
        }
        User user = authenticationService.validateCookieAndGetUser(cookieValue);
        Rating dbRating = ratingRepository.findByUserAndRecipe(user, recipe.get()).orElse(null);
        if(dbRating == null) {
            return 0;
        }
        return dbRating.getScore();
    }

    //update the value of favorite if it exists it deletes it, if it doesn't it creates it
    public String updateFavorite(Long recipeId, String cookieValue) {
        if(cookieValue == null) {
            return "Unauthorized";
        }
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(recipe.isEmpty()) {
            return "Bad request";
        }
        User user = authenticationService.validateCookieAndGetUser(cookieValue);
        // if favorite already exists, remove it
        Favorite dbFavorite = favoriteRepository.findByUserAndRecipe(user, recipe.get()).orElse(null);
        if(dbFavorite != null) {
            favoriteRepository.delete(dbFavorite);
        }else {
            Favorite favorite = new Favorite(user, recipe.get());
            favoriteRepository.save(favorite);
        }
        return "Success";
    }

    //get all favorite recipes of the user
    public List<Recipe> getFavoriteRecipes(String cookieValue) {
        if(cookieValue == null) {
            return new ArrayList<>();
        }
        User user = authenticationService.validateCookieAndGetUser(cookieValue);
        List<Favorite> favoriteRecipes =  favoriteRepository.findAllByUser(user);
        List<Recipe> recipes = new ArrayList<>();
        for(Favorite favorite : favoriteRecipes) {
            recipes.add(favorite.getRecipe());
        }
        return recipes;
    }

    public List<Tag> getTags() {
        return tagRepository.findAll();
    }

    public List<Ingredient> getIngredients() {
        return ingredientRepository.findAll();
    }

    public String createComment(Long recipeId, String content, String cookieValue) {
        if(cookieValue == null) {
            return "Unauthorized";
        }
        User user = authenticationService.validateCookieAndGetUser(cookieValue);
        if(user == null) {
            return "Unauthorized";
        }
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(recipe.isEmpty() || content == null || content.isEmpty()) {
            return "Bad request";
        }
        Comment comment = new Comment(content,user.getUsername());
        comment.setRecipe(recipe.get());
        commentRepository.save(comment);
        return "Success";
    }

    public List<Comment> getCommentsOfRecipe(Long recipeId) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if(recipe.isEmpty()) {
            return new ArrayList<>();
        }
        return commentRepository.findByRecipe(recipe.get()).orElse(new ArrayList<>());
    }

    public List<Recipe> getRandomRecipesByType(String type) {
        if(type == null || type.isEmpty()) {
            return new ArrayList<>();
        }
        if(Arrays.stream(RecipeTypes.values()).noneMatch(recipeType -> recipeType.name().equals(type))) {
            return new ArrayList<>();
        }
        return recipeRepository.findRandomRecipesByType(type);
    }
}
