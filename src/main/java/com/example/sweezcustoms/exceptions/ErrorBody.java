package com.example.sweezcustoms.exceptions;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorBody {
    private String message;
    private int code;
    private Date date;

}
