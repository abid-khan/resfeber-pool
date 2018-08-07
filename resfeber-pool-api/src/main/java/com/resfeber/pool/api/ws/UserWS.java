package com.resfeber.pool.api.ws;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.resfeber.pool.core.type.Gender;

@Data
@Getter
@Setter
public class UserWS extends BaseWS {
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
    private UserWS(String uuid, String status, String externalId, String email, String firstName, String lastName, String givenName, String profile, String avatar, Gender gender, String phone) {
        super(uuid, status);
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
