package com.diary.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatRequestDTO {

    private String model;
    private List<MessageDTO> messages;


}
