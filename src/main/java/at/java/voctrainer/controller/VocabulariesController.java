package at.java.voctrainer.controller;

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
    public String createVocabulary(@PathVariable int vocId, @RequestBody String input) {
        System.out.println("vocId: " + vocId);
        System.out.println("input: " + input);

        return "hello";
    }
}
