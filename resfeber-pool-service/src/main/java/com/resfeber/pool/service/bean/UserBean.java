package com.resfeber.pool.service.bean;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.resfeber.pool.core.type.Gender;

@ToString
@Getter
@Setter
public class UserBean extends BaseBean {
    private String externalId;
    private String email;
    private String firstName;
    private String lastName;
    private String givenName;
    private String profile;
    private String avatar;
    private Gender gender;
    private String phone;

    @Builder
    private UserBean(Long id, String uuid, String status, String externalId, String email, String firstName, String lastName, String givenName, String profile, String avatar, Gender gender, String phone) {
        super(id, uuid, status);
        this.externalId = externalId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.givenName = givenName;
        this.profile = profile;
        this.avatar = avatar;
        this.gender = gender;
        this.phone = phone;
    }
}
