package at.java.voctrainer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vocabulary {
    private Long vocId;
    private String domestic;
    private String foreign;
    private String exerciseDate;
    private VocState state;
}
