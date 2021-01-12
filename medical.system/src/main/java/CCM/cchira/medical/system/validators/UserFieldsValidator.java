package CCM.cchira.medical.system.validators;

import CCM.cchira.medical.system.dto.UserDTO;
import CCM.cchira.medical.system.exception.IncorrectParameterException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

public class UserFieldsValidator {

    private static final Log LOGGER = LogFactory.getLog(UserFieldsValidator.class);
    private static final EmailFieldValidator EMAIL_VALIDATOR = new EmailFieldValidator() ;

    public static void validateInsertOrUpdate(UserDTO userDTO) {

        List<String> errors = new ArrayList<>();
        if (userDTO == null) {
            errors.add("personDTO is null");
            throw new IncorrectParameterException(UserDTO.class.getSimpleName(), errors);
        }
        if (userDTO.getEmail() == null || !EMAIL_VALIDATOR.validate(userDTO.getEmail())) {
            errors.add("Person email has invalid format");
        }

        if (!errors.isEmpty()) {
            LOGGER.error(errors);
            throw new IncorrectParameterException(UserFieldsValidator.class.getSimpleName(), errors);
        }
    }
}
