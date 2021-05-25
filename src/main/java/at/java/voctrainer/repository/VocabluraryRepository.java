package at.java.voctrainer.repository;

import at.java.voctrainer.dao.VocabluraryDto;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 25.05.2021
 */
public interface VocabluraryRepository extends JpaRepository<VocabluraryDto, Long> {
}
