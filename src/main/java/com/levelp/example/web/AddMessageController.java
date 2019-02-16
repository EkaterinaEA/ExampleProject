package com.levelp.example.web;

import com.levelp.example.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Transactional
public class AddMessageController {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private MessageDAO messageDAO;

    @Autowired
    private List<PassportVerificationServise> allVerificationServices;

    @Autowired
    @Qualifier("police")
    private PassportVerificationServise policeVerificationService;

    @GetMapping(path = "/add-message")
    // @RequestMapping (method = RequestMethod.GET, path = "/add-subject")
    public String addMessageForm(@ModelAttribute(name = "message") AddMessageFormBean form){
        return "add-message";
    }

    @PostMapping(path = "/add-message")
    public String postForm(
            @ModelAttribute(name = "message") AddMessageFormBean form,
            // позводит пробросить ошибку:
            BindingResult bindingResult
    ) {
        boolean verified = false;
        // обойдём список сервисов
        for (PassportVerificationServise servise : allVerificationServices) {
            if (servise.isValid(form.getName(), form.getPassportNumber())) {
                verified = true;
                break;
            }
        }
        if (!verified){
            bindingResult.addError(new FieldError("message", "name",
                    "Паспортные данные неверны"));
            return "add-message";
        }

        Room room = null;

        if (room == null) {
            bindingResult.addError(new ObjectError("message", "Нет комнаты"));
            return "add-message";
        }

        try {
            Message created = messageDAO.createMessage(form.getText(), form.getAttachedFiles(), room);
            created.setSubject(form.getSubject());
        } catch (Throwable t){
            bindingResult.addError(new ObjectError("message", "Не удалось создать сообщение: "
            + t.getMessage() + "."));
        return "add-message";
    }
        return "redirect:/";
}
}
