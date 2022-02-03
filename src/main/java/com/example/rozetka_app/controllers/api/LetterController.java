package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.models.Letter;
import com.example.rozetka_app.repositories.LetterRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import com.example.rozetka_app.statuscodes.DefinedErrors;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

@Lazy
@RestController
public class LetterController {
    private final LetterRepository letterRepository;
    private final ResponseService<List<Letter>> responseService;

    public LetterController(
        LetterRepository letterRepository,
        ResponseService<List<Letter>> responseService
    ){
        this.letterRepository = letterRepository;
        this.responseService = responseService;
    }

    @GetMapping(path = "/api/letters/by-date")
    @AdminOnly
    private Object getLettersByDate(
        @RequestParam("date")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date
    ) {
        EnumMap<ResponseDataType, List<Letter>> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULTS, this.letterRepository.findAllByDate(date));

        this.responseService.setEnumData(enumMap);

        return this.responseService;
    }

    @PostMapping(path = "/api/letters")
    private Object createLetter(
        @Valid Letter letter,
        BindingResult bindingResult
    ) {
        if (!bindingResult.hasErrors()) {
            this.letterRepository.save(letter);
            this.responseService.setStatus("ok");
        } else {
            this.responseService.addFullErrorsInfo(DefinedErrors.INVALID_QUERY.getAllInfo());
        }

        return this.responseService;
    }
}
