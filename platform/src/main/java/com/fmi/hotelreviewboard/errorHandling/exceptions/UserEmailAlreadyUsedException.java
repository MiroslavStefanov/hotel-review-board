package com.fmi.hotelreviewboard.errorHandling.exceptions;


import com.fmi.hotelreviewboard.model.dto.UserServiceModel;

public class UserEmailAlreadyUsedException extends UserException {
    private static final String DEFAULT_MESSAGE = "Email already used";

    public UserEmailAlreadyUsedException() {
        super(DEFAULT_MESSAGE);
    }

    public UserEmailAlreadyUsedException(UserServiceModel serviceModel) {
        super(DEFAULT_MESSAGE, serviceModel);
    }
}
