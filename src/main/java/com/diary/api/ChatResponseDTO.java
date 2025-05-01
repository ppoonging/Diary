package com.diary.api;

import lombok.Data;

import java.util.List;

@Data
public class ChatResponseDTO {
    private List<ChoiceDTO> choices;


}
