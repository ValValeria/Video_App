package com.example.rozetka_app.controllers.api;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.annotations.SecurityPermissionsContext;
import com.example.rozetka_app.controllers.api.dto.SearchDto;
import com.example.rozetka_app.models.AppUser;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import com.example.rozetka_app.statuscodes.DefinedErrors;
import com.example.rozetka_app.statuscodes.DefinedStatusCodes;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.rozetka_app.security.AppSecurityUserRolesList.CAN_VIEW_POSTS;

@RestController
@RequestMapping(path = "/api")
public class VideoController {
    private final VideoRepository videoRepository;
    private final ResponseService<Object> responseService;
    private final UserRepository userRepository;
    @Autowired
    public VideoController(
            VideoRepository videoRepository,
            ResponseService<Object> responseService,
            UserRepository userRepository
    ) {
        this.videoRepository = videoRepository;
        this.responseService = responseService;
        this.userRepository = userRepository;
    }

    @GetMapping("/videos/{entityId}")
    @EntityMustExists(classType = Video.class)
    public Object getVideo(@PathVariable Long entityId) {
        EnumMap<ResponseDataType, Object> enumMap = new EnumMap<>(ResponseDataType.class);
        enumMap.put(ResponseDataType.RESULT, this.videoRepository.findVideoById(entityId));

        this.responseService.setEnumData(enumMap);

        return this.responseService;
    }

    @GetMapping("/videos")
    public Object getVideos(Pageable pageable) throws JsonProcessingException {
        final EnumMap<ResponseDataType, Object> hashMap = new EnumMap<>(ResponseDataType.class);
        final Page<Video> videosPage = videoRepository.findAll(pageable);
        final List<Video> videoList = videosPage.toList();

        hashMap.put(ResponseDataType.ALL_PAGES, videosPage.getTotalPages());
        hashMap.put(ResponseDataType.RESULTS, videoList);

        this.responseService.setStatus("ok");
        this.responseService.setEnumData(hashMap);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        return objectMapper.writeValueAsString(this.responseService);
    }

    @PutMapping("/videos")
    @AdminOnly
    public Object uploadVideo(
            @RequestParam() MultipartFile videoFile,
            @Valid() Video video,
            BindingResult bindingResult,
            HttpServletRequest request
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            this.responseService.addFullErrorsInfo(DefinedErrors.INPUT_FIELD_ERRORS.getAllInfo());
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
            String fileName = dateFormat.format(new Date()) + videoFile.getOriginalFilename();
            String path = String.format("/static/uploads/%s", fileName);
            Path filePath = Paths.get("src\\main\\resources\\static\\uploads\\" + fileName);

            Files.createFile(filePath);

            try (
                    InputStream inputStream = videoFile.getInputStream();
                    OutputStream outputStream = Files.newOutputStream(filePath)
            ) {
                int ch;

                while ((ch = inputStream.read()) != -1) {
                    outputStream.write(ch);
                }
            }

            Principal principal = request.getUserPrincipal();
            AppUser user = userRepository.findAppUserByUsername(principal.getName());

            if (user != null) {
                video.setPath(path);
                video.setUser(user);

                videoRepository.save(video);
                responseService.setStatus("ok");
            }
        }

        String key = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);
        this.responseService.setData(Map.of(key, this.responseService));

        return this.responseService;
    }

    @DeleteMapping("/videos/{id}")
    @AdminOnly
    public String deleteVideo(@PathVariable Long id) {
        this.videoRepository.deleteVideoById(id);
        this.responseService.addFullStatusInfo(DefinedStatusCodes.STATUS_OK.getAllInfo());

        return "redirect:/";
    }

    @GetMapping("/auth/search")
    @SecurityPermissionsContext(
            permission = CAN_VIEW_POSTS,
            className = AppUser.class
    )
    private Object search(@RequestBody SearchDto searchDto){
        Pattern pattern = Pattern.compile("[aA-zZ]{1,20}");
        Matcher matcher = pattern.matcher(searchDto.getSearch());

        final PageRequest pageRequest = PageRequest.of(searchDto.getPage(), searchDto.getPer_page(), Sort.by("id"));
        final String resultsKey = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);
        Page<Video> videoPage;

        if(matcher.find()) {
            videoPage = videoRepository.findAllByText("%" + searchDto.getSearch() + "%", Pageable.ofSize(searchDto.getPer_page()));
        } else {
            videoPage = videoRepository.findAll(pageRequest);
        }

        final Map<String, Object> pageDataMap = new LinkedHashMap<>(Map.of(
                resultsKey, videoPage.get().collect(Collectors.toList()),
                "page", videoPage.getTotalPages()
        ));

        this.responseService.setData(pageDataMap);

        return this.responseService;
    }
}
