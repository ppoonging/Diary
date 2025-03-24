package com.diary.user;

public class NaverUser {
    private String code;
    private String state;

    public NaverUser(String code, String state) {
        this.code = code;
        this.state = state;
    }
        public String getCode(){
            return code;
        }
        public String getState(){
            return state;
        }

}
