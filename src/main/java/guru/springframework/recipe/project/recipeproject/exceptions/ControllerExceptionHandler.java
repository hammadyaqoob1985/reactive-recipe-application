package guru.springframework.recipe.project.recipeproject.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
//import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    //Add response status here other 200 will be returned not 404
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public String handleNumberFormatException(Exception exception, Model model) {
        log.error("Handling binding exceptionError");
        log.error(exception.getMessage());

        model.addAttribute("exception", exception);

        return "400error";

    }
}
