package at.java.voctrainer.repository;

import at.java.voctrainer.dao.VocabularyDto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 25.05.2021
 */
public interface VocabluraryRepository extends JpaRepository<VocabularyDto, Long> {
}
