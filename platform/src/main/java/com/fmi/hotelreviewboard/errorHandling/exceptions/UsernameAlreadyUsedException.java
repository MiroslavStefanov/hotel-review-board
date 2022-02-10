package com.fmi.hotelreviewboard.errorHandling.exceptions;


import com.fmi.hotelreviewboard.model.dto.UserServiceModel;

public class UsernameAlreadyUsedException extends UserException {
    private static final String DEFAULT_MESSAGE = "Username already used";

    public UsernameAlreadyUsedException() {
        super(DEFAULT_MESSAGE);
    }

    public UsernameAlreadyUsedException(UserServiceModel serviceModel) {
        super(DEFAULT_MESSAGE, serviceModel);
    }
}
