package at.java.voctrainer.controller;

import at.java.voctrainer.dao.VocabularyDto;
import at.java.voctrainer.model.VocState;
import at.java.voctrainer.model.Vocabulary;
import at.java.voctrainer.model.VocabularyList;
import at.java.voctrainer.repository.VocabluraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 20.05.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VocabulariesControllerGetTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VocabluraryRepository vocabluraryRepository;



    @Test
    public void given_vocabularyInDb_when_getAllVocabulary_then_returnAll(){
        String testForeign = "testForeign";
        String testDomestic = "testDomestic";
        String testExerciseDate = "01-06-2021";
        VocState testState = VocState.DAILY;

        int beforeRun = vocabluraryRepository.findAll().size();

        VocabularyDto voc01 = VocabularyDto.builder().domesticText(testDomestic).foreignText(testForeign)
                .exerciseDate(testExerciseDate).state(testState).build();

        VocabularyDto voc02 = VocabularyDto.builder().domesticText(testDomestic).foreignText(testForeign)
                .exerciseDate(testExerciseDate).state(testState).build();

        voc01 = vocabluraryRepository.save(voc01);
        long voc01Id = voc01.getId();
        voc02 = vocabluraryRepository.save(voc02);

        int afterRun = vocabluraryRepository.findAll().size();
        ResponseEntity<VocabularyList> response = executeCallToRestTemplate();
        assertThat(response.getBody().getVocabularies().size()).isEqualTo(beforeRun+2);
        assertThat(response.getBody().getVocabularies().size()).isEqualTo(afterRun);

        Vocabulary voc = response.getBody().getVocabularies().stream().filter(v -> v.getVocId()==voc01Id).findFirst().get();
        assertThat(voc.getVocId()).isEqualTo(voc01.getId());
        assertThat(voc.getDomestic()).isEqualTo(testDomestic);
        assertThat(voc.getForeign()).isEqualTo(testForeign);
        assertThat(voc.getState()).isEqualTo(testState);
        assertThat(voc.getExerciseDate()).isEqualTo(testExerciseDate);
    }


    private ResponseEntity<VocabularyList> executeCallToRestTemplate() {
        ResponseEntity<VocabularyList> ret = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            String baseUrl = "http://localhost:" + port + "/vocabularies/";
            URI uri = new URI(baseUrl);

            ret = restTemplate.getForEntity(uri, VocabularyList.class);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
