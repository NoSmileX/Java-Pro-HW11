package com.example.urlshorten.controller;

import com.example.urlshorten.dto.UrlResultDTO;
import com.example.urlshorten.service.UrlService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @GetMapping("/")
    public String index(Model model) {
        var allUrls = urlService.getAllUrl();
        model.addAttribute("list", allUrls);
        return "index";
    }

    @PostMapping("/save")
    public String shorten(@RequestParam String url) {
        if (!url.isEmpty()) {
            long id = urlService.saveUrl(url);

            var result = new UrlResultDTO();
            result.setUrl(url);
            result.setShortUrl(Long.toString(id));
        }
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteUrl(@RequestParam Long id) {
        urlService.deleteUrl(id);
        return "redirect:/";
    }

    @GetMapping("my/{id}")
    public ResponseEntity<Void> redirect(@PathVariable("id") Long id) {
        var url = urlService.getUrl(id);

        var headers = new HttpHeaders();
        headers.setLocation(URI.create(url));
        headers.setCacheControl("no-cache, no-store, must-revalidate");

        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


}
