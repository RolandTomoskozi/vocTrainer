package at.java.voctrainer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VocabularyCreate {
    private String domestic;
    private String foreign;
}
