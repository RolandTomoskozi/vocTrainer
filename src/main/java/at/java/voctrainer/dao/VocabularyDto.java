package at.java.voctrainer.dao;

import at.java.voctrainer.model.VocState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 25.05.2021
 */
@Entity
@Table(name = "Vocabulary")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VocabularyDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String domesticText;
    private String foreignText;
    private String exerciseDate;
    private VocState state;
}
