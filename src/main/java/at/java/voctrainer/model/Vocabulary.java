package at.java.voctrainer.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Vocabulary {
    private Long vocId;
    private String domestic;
    private String foreign;
    private String exerciseDate;
    private VocState state;
}
