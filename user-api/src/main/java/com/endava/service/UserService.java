package com.endava.service;

import com.endava.model.User;
import com.endava.model.dto.UpdateUserDto;
import com.endava.model.dto.external.UserAssignment;
import com.endava.specification.SpecificationParams;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUserEgn(String egn);

    Optional<User> findByUserEmail(String email);

    User createUser(User user);

    User updateUser(UpdateUserDto updateUserDto, User foundUser);

    String deactivateUser(String egn, User findUser);

    List<UserAssignment> getSpecificationUsers(SpecificationParams specificationParams, List<Long> usersId);
}
