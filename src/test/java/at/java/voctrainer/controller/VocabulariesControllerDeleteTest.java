package at.java.voctrainer.controller;

import at.java.voctrainer.dao.VocabularyDto;
import at.java.voctrainer.model.VocState;
import at.java.voctrainer.repository.VocabluraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 24.06.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VocabulariesControllerDeleteTest {
    @LocalServerPort
    private int port;

    @Autowired
    private VocabluraryRepository vocabluraryRepository;

    int afterRun;
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
    public void given_vocabularyInDB_when_deleteVocabulary_then_deleteVocabulary() {
        executeCallToRestTemplateSingleVocabularies(voc01Id);
        assertThat(vocabluraryRepository.findById(voc01Id)).isNotPresent();
    }

    private void executeCallToRestTemplateSingleVocabularies(long vocId) {
        String baseUrl = "http://localhost:" + port + "/vocabularies/" + vocId;

        RequestEntity<Void> request = RequestEntity
                .delete(baseUrl)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        new RestTemplate().exchange(request, Void.class);
    }

    @Test
    public void given_nonExistingVocId_when_deleteVocabulary_then_returnHttp404() {
        assertThatExceptionOfType(HttpClientErrorException.class)
                .isThrownBy(() -> {
                    executeCallToRestTemplateSingleVocabularies(99999L);
                });
    }
}
