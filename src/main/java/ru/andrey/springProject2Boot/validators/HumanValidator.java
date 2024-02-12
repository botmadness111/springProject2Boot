package ru.andrey.springProject2Boot.validators;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.andrey.springProject2Boot.models.Human;
import ru.andrey.springProject2Boot.services.HumanService;

@Component
public class HumanValidator implements Validator {

    HumanService humanService;

    @Autowired
    public HumanValidator(HumanService humanService) {
        this.humanService = humanService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Human.class == clazz;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Human human = (Human) target;

        if (humanService.getHuman(human.getFio()).isPresent()) {
            errors.rejectValue("fio", "", "FIO should be UNIQUE!");
        }
    }
}
