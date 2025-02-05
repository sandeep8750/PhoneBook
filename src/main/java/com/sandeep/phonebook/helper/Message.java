package com.sandeep.phonebook.helper;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Message {
    private String content;
    @Builder.Default
    private Color color = Color.red;
}



