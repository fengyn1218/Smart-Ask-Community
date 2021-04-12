package com.feng.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @author: fengyunan
 * Created on: 2021-04-12
 */
@Data
public class TagsDTO {
    private String categoryName;
    private List<String> tags;
}
