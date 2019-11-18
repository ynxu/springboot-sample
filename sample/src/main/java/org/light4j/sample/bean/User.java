package org.light4j.sample.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Data
@ApiModel(value = "User", description = "用户对象描述")
public class User implements Serializable {
    @Id
    private Long id;

    @ApiModelProperty(name = "username", value = "用户名称", dataType = "String")
    @NotBlank
    @Column(updatable = false, nullable = false)
    private String username;

    @ApiModelProperty(name = "password", value = "用户密码", dataType = "String")
    @NotBlank
    @Size(min = 6, max = 14)
    private String password;

    @ApiModelProperty(name = "email", value = "用户邮箱", dataType = "String")
    @Email
    @Size(max = 50)
    private String email;

}
