package ru.one.stream.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemDto {

    private int position;

    private String title;

    private String url;

    private Double duration;

    private Boolean isNeedProxy;

}
