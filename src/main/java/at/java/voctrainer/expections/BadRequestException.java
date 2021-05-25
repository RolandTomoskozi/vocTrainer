package at.java.voctrainer.expections;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Roland Tömösközi (roland.toemoeskoezi@outlook.com)
 * Created on 25.05.2021
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

}
