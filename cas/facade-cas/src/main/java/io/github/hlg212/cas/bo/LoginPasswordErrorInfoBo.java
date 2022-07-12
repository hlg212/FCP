package io.github.hlg212.cas.bo;

import lombok.Data;


@Data
public class LoginPasswordErrorInfoBo  {

    private String username;
    private Integer currNum;
    private Integer maxErrorNum;

}
