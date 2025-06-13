package top.liuqiao.nextTop.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author liuqiao
 * @since 2025-06-13
 */
@Data
public class UserRegisterRequest implements Serializable {

    /**
     *
     */
    @NotBlank
    @Length(min = 8, max = 16)
    private String account;

    /**
     *
     */
    @NotBlank
    @Length(min = 8, max = 16)
    private String password;


    /**
     *
     */
    @Length(max = 16)
    private String username;

    /**
     *
     */
    @Length(max = 511)
    private String avatarUrl;


    private final static long serialVersionUID = -1;
}
