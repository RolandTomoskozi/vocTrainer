package at.java.voctrainer.controller;


import at.java.voctrainer.dao.VocabularyDto;
import at.java.voctrainer.expections.BadRequestException;
import at.java.voctrainer.model.VocState;
import at.java.voctrainer.model.Vocabulary;
import at.java.voctrainer.model.VocabularyCreate;
import at.java.voctrainer.model.VocabularyList;
import at.java.voctrainer.repository.VocabluraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 20.05.2021
 */
@RestController
public class VocabulariesController {
    @Autowired
    private VocabluraryRepository vocabluraryRepository;

    @PostMapping(value = "/vocabularies")
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

        VocabularyDto vocabluraryDto = new VocabularyDto();
        vocabluraryDto.setDomesticText(input.getDomestic());
        vocabluraryDto.setForeignText(input.getForeign());
        vocabluraryDto.setState(VocState.DAILY);
        vocabluraryDto.setExerciseDate(expectedDate);

        VocabularyDto savedDto = vocabluraryRepository.save(vocabluraryDto);

        return Vocabulary.builder()
                .vocId(savedDto.getId())
                .domestic(input.getDomestic())
                .foreign(input.getForeign())
                .state(VocState.DAILY)
                .exerciseDate(expectedDate)
                .build();
    }

    @GetMapping(value = "/vocabularies")
    public VocabularyList getVocabulary(){
        List<VocabularyDto> allVocabulary = vocabluraryRepository.findAll();

        VocabularyList vocabularyList = new VocabularyList();
        vocabularyList.setVocabularies(new ArrayList<>());

        for (VocabularyDto voc : allVocabulary) {
          Vocabulary vocabulary = Vocabulary.builder()
                  .vocId(voc.getId())
                  .domestic(voc.getDomesticText())
                  .exerciseDate(voc.getExerciseDate())
                  .foreign(voc.getForeignText())
                  .state(voc.getState())
                  .build();

          vocabularyList.getVocabularies().add(vocabulary);
        }

        return vocabularyList;
    }
}
