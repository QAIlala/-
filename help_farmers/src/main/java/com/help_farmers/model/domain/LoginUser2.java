package com.help_farmers.model.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.help_farmers.common.utils.CustomAuthorityDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Author: WZ
 * @Date: 2024/3/12 21:43
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser2 {

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    private Integer roleId;

    private String roleName;

    @JsonDeserialize(using = CustomAuthorityDeserializer.class)
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}
