package at.java.voctrainer.expections;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 15.06.2021
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

}
