package com.example.uaa.model.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class UserVo implements Serializable {
    @Serial
    private static final long serialVersionUID = -1438475440232405071L;
    private String name;
}
