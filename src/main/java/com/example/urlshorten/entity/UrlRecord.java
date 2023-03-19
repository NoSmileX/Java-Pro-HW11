package com.example.urlshorten.entity;

import com.example.urlshorten.dto.UrlDTO;
import com.example.urlshorten.dto.UrlStatDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@EqualsAndHashCode
@Entity
public class UrlRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String url; // long URL

    @Column(nullable = false)
    private Long count;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date lastAccess;

    public UrlRecord() {
        count = 0L;
        lastAccess = new Date();
    }

    public UrlRecord(String url) {
        this();
        this.url = url;
    }

    public static UrlRecord of(String url) {
        return new UrlRecord(url);
    }

    public UrlStatDTO toStatDTO() {
        var result = new UrlStatDTO();

        result.setUrl(url);
        result.setShortUrl(Long.toString(id));
        result.setRedirects(count);
        result.setLastAccess(lastAccess);

        return result;
    }
}
