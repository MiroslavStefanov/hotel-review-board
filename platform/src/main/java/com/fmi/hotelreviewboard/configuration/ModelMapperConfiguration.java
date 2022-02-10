package com.fmi.hotelreviewboard.configuration;

import com.fmi.hotelreviewboard.model.dto.UserServiceModel;
import com.fmi.hotelreviewboard.model.entity.User;
import com.fmi.hotelreviewboard.model.view.UserLinkViewModel;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfiguration {
    private final ModelMapper mapper;


    public ModelMapperConfiguration() {
        mapper = new ModelMapper();
        this.configure();
    }

    @Bean
    public ModelMapper getModelMapper() {
        return this.mapper;
    }

    private void configure() {
        //adding converters
        this.addConverters();
    }

    private void addConverters() {
        Converter<Set<User>, Set<UserServiceModel>> userSetServiceCon = ctx -> ctx.getSource()
                .stream()
                .map(u -> this.mapper.map(u, UserServiceModel.class))
                .collect(Collectors.toSet());


        Converter<Set<UserServiceModel>, Set<UserLinkViewModel>> userSetLinkViewCon = ctx -> ctx.getSource()
                .stream()
                .map(u -> this.mapper.map(u, UserLinkViewModel.class))
                .collect(Collectors.toSet());


        this.mapper.addConverter(userSetServiceCon);
        this.mapper.addConverter(userSetLinkViewCon);
    }
}
