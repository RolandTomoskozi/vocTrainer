package at.java.voctrainer.repository;

import at.java.voctrainer.dao.VocabularyDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 25.05.2021
 */
public interface VocabluraryRepository extends JpaRepository<VocabularyDto, Long> {

    @Modifying
    @Query("delete from VocabularyDto v where v.id=?1")
    int deleteByIdWithCount(Long id);
}
