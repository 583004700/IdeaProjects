package com.example.uaa.model.bo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = -774117303354020632L;
    private Long id;
    private String name;
}
