package at.java.voctrainer.controller;

import at.java.voctrainer.dao.VocabluraryDto;
import at.java.voctrainer.model.VocState;
import at.java.voctrainer.model.Vocabulary;
import at.java.voctrainer.model.VocabularyCreate;
import at.java.voctrainer.repository.VocabluraryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 20.05.2021
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VocabulariesControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VocabluraryRepository vocabluraryRepository;

    @Test
    public void given_ValidData_when_createVocabulary_then_createVocabulary() {

        RequestEntity<VocabularyCreate> requestEntity = null;
        String testDomestic = "domestic";
        String testForeign = "foreign";
        VocabularyCreate input = VocabularyCreate.builder().domestic(testDomestic).foreign(testForeign).build();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // heutige Datum
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);  // number of days to add
        String expectedDate = format.format(c.getTime());

        requestEntity = executeCallToRestTemplate(requestEntity, input);
        ResponseEntity<Vocabulary> ret = restTemplate.exchange(requestEntity, Vocabulary.class);
        ResponseEntity<Vocabulary> ret2 = restTemplate.exchange(requestEntity, Vocabulary.class);

        List<VocabluraryDto> vocList = vocabluraryRepository.findAll();
        assertThat(vocList.size()).isEqualTo(2);

        Optional<VocabluraryDto> byId = vocabluraryRepository.findById(ret.getBody().getVocId());
        assertThat(byId.isPresent()).isTrue();

        Optional<VocabluraryDto> byId2 = vocabluraryRepository.findById(ret2.getBody().getVocId());
        assertThat(byId2.isPresent()).isTrue();

        // check if ids are unique
        assertThat(byId.get().getId()).isNotEqualTo(byId2.get().getId());

        assertThat(ret.getBody().getVocId()).isNotNull();
        assertThat(ret.getBody().getDomestic()).isEqualTo(testDomestic);
        assertThat(ret.getBody().getForeign()).isEqualTo(testForeign);
        assertThat(ret.getBody().getState()).isEqualTo(VocState.DAILY);
        assertThat(ret.getBody().getExerciseDate()).isEqualTo(expectedDate);
    }

    @Test
    public void when_null_domestic_then_http_400() {

        RequestEntity<VocabularyCreate> requestEntity = null;
        String testDomestic = null;
        String testForeign = "foreign";
        VocabularyCreate input = VocabularyCreate.builder().domestic(testDomestic).foreign(testForeign).build();

        requestEntity = executeCallToRestTemplate(requestEntity, input);
        ResponseEntity<Vocabulary> ret = restTemplate.exchange(requestEntity, Vocabulary.class);

        assertThat(ret.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void when_blank_domestic_then_http_400() {

        RequestEntity<VocabularyCreate> requestEntity = null;
        String testDomestic = "  ";
        String testForeign = "foreign";
        VocabularyCreate input = VocabularyCreate.builder().domestic(testDomestic).foreign(testForeign).build();

        requestEntity = executeCallToRestTemplate(requestEntity, input);
        ResponseEntity<Vocabulary> ret = restTemplate.exchange(requestEntity, Vocabulary.class);

        assertThat(ret.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void when_null_foreign_then_http_400() {

        RequestEntity<VocabularyCreate> requestEntity = null;
        String testDomestic = "domestic";
        String testForeign = null;
        VocabularyCreate input = VocabularyCreate.builder().domestic(testDomestic).foreign(testForeign).build();

        requestEntity = executeCallToRestTemplate(requestEntity, input);
        ResponseEntity<Vocabulary> ret = restTemplate.exchange(requestEntity, Vocabulary.class);

        assertThat(ret.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void when_blank_foreign_then_http_400() {

        RequestEntity<VocabularyCreate> requestEntity = null;
        String testDomestic = "domestic";
        String testForeign = "   ";
        VocabularyCreate input = VocabularyCreate.builder().domestic(testDomestic).foreign(testForeign).build();

        requestEntity = executeCallToRestTemplate(requestEntity, input);
        ResponseEntity<Vocabulary> ret = restTemplate.exchange(requestEntity, Vocabulary.class);

        assertThat(ret.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    private <T> RequestEntity<T> executeCallToRestTemplate(RequestEntity<T> requestEntity, T input) {
        RequestEntity<T> ret = null;
        try {
            ret = RequestEntity.post(new URL("http://localhost:" + port + "/vocabularies/1")
                    .toURI()).contentType(MediaType.APPLICATION_JSON).body(input);
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
        return ret;
    }
}