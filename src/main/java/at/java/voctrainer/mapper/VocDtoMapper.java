package at.java.voctrainer.mapper;

import at.java.voctrainer.dao.VocabularyDto;
import at.java.voctrainer.model.Vocabulary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@activesolution.at)
 * Created on 15.06.2021
 */
@Mapper
public interface VocDtoMapper {

    VocDtoMapper INSTANCE = Mappers.getMapper(VocDtoMapper.class );

    @Mapping(source = "vocId", target = "id")
    @Mapping(source = "domestic", target = "domesticText")
    @Mapping(source = "foreign", target = "foreignText")
    VocabularyDto modelToDto(Vocabulary vocabulary);

    @Mapping(source = "id", target = "vocId")
    @Mapping(source = "domesticText", target = "domestic")
    @Mapping(source = "foreignText", target = "foreign")
    Vocabulary dtoToModel(VocabularyDto vocabularyDto);
}
