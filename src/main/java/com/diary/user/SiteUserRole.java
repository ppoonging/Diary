package com.diary.user;

import lombok.Getter;

@Getter
public enum SiteUserRole {
ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private SiteUserRole(String value){
        this.value= value;
    }
    private String value;
}
