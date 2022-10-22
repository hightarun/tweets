package com.tweetapp.tweets.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ExceptionDetails {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timeStamp;

    private Map<String,String> errorMap;

    public ExceptionDetails(LocalDateTime timeStamp,Map<String,String> errorMap){
        super();
        this.timeStamp = timeStamp;
        this.errorMap= errorMap;
    }
}