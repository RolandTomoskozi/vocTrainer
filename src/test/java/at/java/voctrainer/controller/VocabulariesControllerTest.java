package at.java.voctrainer.controller;

import at.java.voctrainer.model.VocState;
import at.java.voctrainer.model.Vocabulary;
import at.java.voctrainer.model.VocabularyCreate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    @Test
    public void given_ValidData_when_createVocabulary_then_createVocabulary() {

        RequestEntity<VocabularyCreate> requestEntity = null;
        String testDomestic = "domestic";
        String testForeign = "foreign";
        VocabularyCreate input = VocabularyCreate.builder().domestic(testDomestic).foreign(testForeign).build();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);  // number of days to add
        String expectedDate = format.format(c.getTime());
        requestEntity = executeCallToRestTemplate(requestEntity, input);
        ResponseEntity<Vocabulary> ret = restTemplate.exchange(requestEntity, Vocabulary.class);

        assertThat(ret.getBody().getVocId()).isNotNull();
        assertThat(ret.getBody().getDomestic()).isEqualTo(testDomestic);
        assertThat(ret.getBody().getForeign()).isEqualTo(testForeign);
        assertThat(ret.getBody().getState()).isEqualTo(VocState.DAILY);
//        assertThat(ret.getBody().getExerciseDate()).isEqualTo(expectedDate);
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