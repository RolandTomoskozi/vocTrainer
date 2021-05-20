package at.java.voctrainer.controller;

import at.java.voctrainer.model.VocState;
import at.java.voctrainer.model.Vocabulary;
import at.java.voctrainer.model.VocabularyCreate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 20.05.2021
 */
@RestController
public class VocabulariesController {

    @PostMapping("/vocabularies/{vocId}")
    public Vocabulary createVocabulary(@PathVariable int vocId, @RequestBody VocabularyCreate input) {
        System.out.println("vocId: " + vocId);
        System.out.println("input: " + input);

        return Vocabulary.builder().domestic(input.getDomestic()).foreign(input.getForeign()).state(VocState.DAILY)
                .build();
    }
}
