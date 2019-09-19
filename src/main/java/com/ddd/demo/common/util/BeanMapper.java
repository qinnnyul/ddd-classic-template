package com.ddd.demo.common.util;

import org.modelmapper.ModelMapper;

public final class BeanMapper {
    private static final ModelMapper mapper = new ModelMapper();

    private BeanMapper() {
    }

    public static ModelMapper instance() {
        return mapper;
    }
}
