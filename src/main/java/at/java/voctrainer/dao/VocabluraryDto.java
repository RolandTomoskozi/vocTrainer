package at.java.voctrainer.dao;

import at.java.voctrainer.model.VocState;
import lombok.Data;

import javax.persistence.*;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 25.05.2021
 */
@Entity
@Table(name = "Vocablurary")
@Data
public class VocabluraryDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String domesticText;
    private String foreignText;
    private String exerciseDate;
    private VocState state;
}
