package com.example.rozetka_app.controllers;

import com.example.rozetka_app.annotations.AdminOnly;
import com.example.rozetka_app.annotations.EntityMustExists;
import com.example.rozetka_app.models.User;
import com.example.rozetka_app.models.Video;
import com.example.rozetka_app.repositories.UserRepository;
import com.example.rozetka_app.repositories.VideoRepository;
import com.example.rozetka_app.services.ResponseDataType;
import com.example.rozetka_app.services.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class VideoController {
    private final VideoRepository videoRepository;
    private final ResponseService<Object> responseService;
    private final UserRepository userRepository;

    @Autowired
    public VideoController(VideoRepository videoRepository,
                           ResponseService<Object> responseService,
                           UserRepository userRepository
                           ){
        this.videoRepository = videoRepository;
        this.responseService = responseService;
        this.userRepository = userRepository;
    }

    @GetMapping("/videos/{entityId}")
    @EntityMustExists(classType = Video.class)
    public ModelAndView getVideo(@PathVariable Long entityId){
       Video video = videoRepository.findVideoById(entityId);
       ModelAndView modelAndView = new ModelAndView("video");
       String key = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);
       modelAndView.addObject(key, this.responseService);

       responseService.setData(Map.of(key, video));

       return modelAndView;
    }

    @GetMapping("/videos")
    public ModelAndView getVideos(@RequestParam int page,
                          @RequestParam int per_page,
                          HttpServletResponse response
                          ) throws IOException {
        ModelAndView view = new ModelAndView("videos-list");
        String key = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);
        view.addObject(key, this.responseService);

        if(page < 0 || per_page < 0){
           final String redirectUrl = ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/error").toUriString();
           response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
           response.sendRedirect(redirectUrl);
        }

        Page<Video> videosPage = videoRepository.findAll(PageRequest.of(page, per_page));
        HashMap<String, Object> hashMap = new HashMap<>();

        if(videosPage.hasContent()){
            hashMap.put(key, videosPage.get().collect(Collectors.toList()));
        } else{
            hashMap.put(key, Collections.emptyList());
        }

        responseService.setData(Map.of(key, hashMap));

        return view;
    }

    @PutMapping("/videos")
    @AdminOnly
    public ModelAndView uploadVideo(@RequestParam() MultipartFile videoFile,
                            @Valid() Video video,
                            BindingResult bindingResult,
                            HttpServletRequest request
                            ) throws IOException {
        if(bindingResult.hasErrors()){
            this.responseService.setErrors(new String[]{"Check the validity of fields"});
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
            String fileName = dateFormat.format(new Date()) + videoFile.getOriginalFilename();
            String path = String.format("/static/uploads/%s", fileName);
            Path filePath = Paths.get("src\\main\\resources\\static\\uploads\\"+fileName);

            Files.createFile(filePath);

            try(InputStream inputStream = videoFile.getInputStream();
                OutputStream outputStream = Files.newOutputStream(filePath)
            ){
                int ch;

                while((ch=inputStream.read())!=-1){
                    outputStream.write(ch);
                }
            }

            Principal principal = request.getUserPrincipal();
            User user = userRepository.findByUsername(principal.getName());

            if(user != null){
                video.setPath(path);
                video.setAuthor(user);

                videoRepository.save(video);
                responseService.setStatus("ok");
            }
        }

        ModelAndView view = new ModelAndView("videos-list");
        String key = ResponseDataType.RESULTS.name().toLowerCase(Locale.ROOT);
        view.addObject(key, this.responseService);

        return view;
    }

    @DeleteMapping("/videos/{id}")
    @AdminOnly
    public String deleteVideo(@PathVariable Long id){
        videoRepository.deleteVideoById(id);

        return "redirect:/";
    }
}
