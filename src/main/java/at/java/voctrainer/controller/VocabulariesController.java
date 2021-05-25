package at.java.voctrainer.controller;

import at.java.voctrainer.dao.VocabluraryDto;
import at.java.voctrainer.expections.BadRequestException;
import at.java.voctrainer.model.VocState;
import at.java.voctrainer.model.Vocabulary;
import at.java.voctrainer.model.VocabularyCreate;
import at.java.voctrainer.repository.VocabluraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 20.05.2021
 */
@RestController
public class VocabulariesController {
    @Autowired
    private VocabluraryRepository vocabluraryRepository;

    @PostMapping("/vocabularies")
    public Vocabulary createVocabulary(@RequestBody VocabularyCreate input) {
        //System.out.println("vocId: " + vocId);
        System.out.println("input: " + input);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);  // number of days to add
        String expectedDate = format.format(c.getTime());

        if (Objects.isNull(input.getDomestic())
                || input.getDomestic().isBlank()
                || Objects.isNull(input.getForeign())
                || input.getForeign().isBlank()) {
            throw new BadRequestException();
        }

        VocabluraryDto vocabluraryDto = new VocabluraryDto();
        vocabluraryDto.setDomesticText(input.getDomestic());
        vocabluraryDto.setForeignText(input.getForeign());
        vocabluraryDto.setState(VocState.DAILY);
        vocabluraryDto.setExerciseDate(expectedDate);

        VocabluraryDto savedDto = vocabluraryRepository.save(vocabluraryDto);

        return Vocabulary.builder()
                .vocId(savedDto.getId())
                .domestic(input.getDomestic())
                .foreign(input.getForeign())
                .state(VocState.DAILY)
                .exerciseDate(expectedDate)
                .build();
    }
}
