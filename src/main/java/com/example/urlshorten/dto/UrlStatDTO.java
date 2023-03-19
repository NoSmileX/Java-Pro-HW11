package com.example.urlshorten.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class UrlStatDTO extends UrlResultDTO{
    private long redirects;
    private Date lastAccess;
}
