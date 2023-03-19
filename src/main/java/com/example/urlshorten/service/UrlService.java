package com.example.urlshorten.service;

import com.example.urlshorten.dto.UrlDTO;
import com.example.urlshorten.dto.UrlStatDTO;
import com.example.urlshorten.entity.UrlRecord;
import com.example.urlshorten.entity.UrlRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public long saveUrl(String url) {
        var urlRecord = urlRepository.findByUrl(url);
        if (urlRecord == null) {
            urlRecord = UrlRecord.of(url);
            urlRepository.save(urlRecord);
        }

        return urlRecord.getId();
    }

    @Transactional
    public boolean deleteUrl(long id) {
        var urlRecord = urlRepository.findById(id);
        if (urlRecord.isPresent()) {
            urlRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public String getUrl(long id) {
        var urlOpt = urlRepository.findById(id);
        if (urlOpt.isEmpty())
            return null;

        var urlRecord = urlOpt.get();
        urlRecord.setCount(urlRecord.getCount() + 1);
        urlRecord.setLastAccess(new Date());

        return urlRecord.getUrl();
    }

    @Transactional
    public List<UrlRecord> getAllUrl(){
        return urlRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<UrlStatDTO> getStatistics() {
        var records = urlRepository.findAll();
        var result = new ArrayList<UrlStatDTO>();

        records.forEach(x -> result.add(x.toStatDTO()));

        return result;
    }
}
