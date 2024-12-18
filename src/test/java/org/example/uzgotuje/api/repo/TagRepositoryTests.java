package org.example.uzgotuje.api.repo;

import org.example.uzgotuje.database.entity.recipe.Tag;
import org.example.uzgotuje.database.entity.recipe.TagTypes;
import org.example.uzgotuje.database.repository.recipe.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TagRepositoryTests {
    @Autowired
    private TagRepository tagRepository;

    @Test
    public void testSave(){
        // given
        Tag tag = new Tag(TagTypes.DIET.toString(),"vegan");

        // when
        Tag savedTag = tagRepository.save(tag);

        // then
        assertThat(savedTag).isNotNull();
        assertThat(savedTag.getId()).isNotNull();
        assertThat(savedTag.getName()).isEqualTo(tag.getName());
    }

    @Test
    public void testFindAll(){
        // given
        Tag[] tags = {
                new Tag(TagTypes.DIET.toString(),"vegan"),
                new Tag(TagTypes.DIET.toString(),"vegetarian"),
                new Tag(TagTypes.DIET.toString(),"pescetarian"),
                new Tag(TagTypes.DIET.toString(),"gluten-free"),
                new Tag(TagTypes.DIET.toString(),"lactose-free"),
                new Tag(TagTypes.DIET.toString(),"low-carb"),
                new Tag(TagTypes.DIET.toString(),"low-fat"),
                new Tag(TagTypes.DIET.toString(),"low-sugar"),
        };
        tagRepository.saveAll(Arrays.asList(tags));

        // when
        List<Tag> foundTags = tagRepository.findAll();

        // then
        assertThat(foundTags).isNotNull();
        assertThat(foundTags.size()).isEqualTo(8);
    }

    @Test
    public void testFindByName(){
        // given
        Tag tag = new Tag(TagTypes.DIET.toString(),"vegan");
        tagRepository.save(tag);

        // when
        Optional<Tag> foundTag = tagRepository.findByName("vegan");

        // then
        assertThat(foundTag).isPresent();
        assertThat(foundTag.get().getName()).isEqualTo(tag.getName());
    }

    @Test
    public void testFindByNameWithoutMatch(){
        // given
        Tag tag = new Tag(TagTypes.DIET.toString(),"vegan");
        tagRepository.save(tag);

        // when
        Optional<Tag> foundTag = tagRepository.findByName("vegetarian");

        // then
        assertThat(foundTag).isEmpty();
    }

    @Test
    public void testFindById(){
        // given
        Tag tag = new Tag(TagTypes.DIET.toString(),"vegan");
        Tag savedTag = tagRepository.save(tag);

        // when
        Optional<Tag> foundTag = tagRepository.findById(savedTag.getId());

        // then
        assertThat(foundTag).isPresent();
        assertThat(foundTag.get().getName()).isEqualTo(tag.getName());
    }

    @Test
    public void testFindByIdWithoutMatch(){
        // given
        Tag tag = new Tag(TagTypes.DIET.toString(),"vegan");
        tagRepository.save(tag);

        // when
        Optional<Tag> foundTag = tagRepository.findById(100L);

        // then
        assertThat(foundTag).isEmpty();
    }

    @Test
    public void testDeleteById(){
        // given
        Tag tag = new Tag(TagTypes.DIET.toString(),"vegan");
        Tag savedTag = tagRepository.save(tag);

        // when
        tagRepository.deleteById(savedTag.getId());
        Optional<Tag> foundTag = tagRepository.findById(savedTag.getId());

        // then
        assertThat(foundTag).isEmpty();
    }
}
