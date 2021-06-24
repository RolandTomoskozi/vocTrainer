package at.java.voctrainer.controller;

import at.java.voctrainer.dao.VocabularyDto;
import at.java.voctrainer.model.VocState;
import at.java.voctrainer.model.Vocabulary;
import at.java.voctrainer.model.VocabularyCreate;
import at.java.voctrainer.repository.VocabluraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 24.06.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VocabulariesControllerUpdateTest {
    int afterRun;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private VocabluraryRepository vocabluraryRepository;
    private int beforeRun;
    private long voc01Id;
    private VocabularyDto voc01;
    private VocabularyDto voc02;

    @BeforeEach
    public void setUp() {
        String testForeign = "testForeign";
        String testDomestic = "testDomestic";
        String testExerciseDate = "01-06-2021";
        VocState testState = VocState.DAILY;

        beforeRun = vocabluraryRepository.findAll().size();

        voc01 = VocabularyDto.builder().domesticText(testDomestic).foreignText(testForeign)
                .exerciseDate(testExerciseDate).state(testState).build();

        voc02 = VocabularyDto.builder().domesticText(testDomestic).foreignText(testForeign)
                .exerciseDate(testExerciseDate).state(testState).build();

        voc01 = vocabluraryRepository.save(voc01);
        voc01Id = voc01.getId();
        voc02 = vocabluraryRepository.save(voc02);

        afterRun = vocabluraryRepository.findAll().size();

        assertThat(beforeRun).isLessThan(afterRun);
        assertThat(afterRun).isEqualTo(beforeRun + 2);
    }

    @Test
    public void given_vocabularyInDB_when_updateVocabulary_then_updateVocabulary() {
        String testForeignUpdated = "testForeignUpdated";
        String testDomesticUpdated = "testDomesticUpdated";

        VocabularyCreate vocabularyCreate = VocabularyCreate.builder()
                .foreign(testForeignUpdated)
                .domestic(testDomesticUpdated)
                .build();

        ResponseEntity<Vocabulary> response = executeCallToRestTemplateSingleVocabularies(voc01Id, vocabularyCreate);
        Vocabulary voc = response.getBody();
        assert voc != null;
        assertThat(voc.getVocId()).isEqualTo(voc01.getId());
        assertThat(voc.getDomestic()).isEqualTo(testDomesticUpdated);
        assertThat(voc.getForeign()).isEqualTo(testForeignUpdated);
        assertThat(voc.getState()).isEqualTo(voc01.getState());
        assertThat(voc.getExerciseDate()).isEqualTo(voc01.getExerciseDate());
    }

    private ResponseEntity<Vocabulary> executeCallToRestTemplateSingleVocabularies(long vocId, VocabularyCreate body) {
        ResponseEntity<Vocabulary> ret;
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = "http://localhost:" + port + "/vocabularies/" + vocId;
        RequestEntity<VocabularyCreate> request = RequestEntity
                .put(baseUrl)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);

        ret = restTemplate.exchange(request, Vocabulary.class);
        return ret;
    }
}
