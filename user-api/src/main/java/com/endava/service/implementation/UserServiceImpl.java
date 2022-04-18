package com.endava.service.implementation;

import com.endava.client.AssignmentClient;
import com.endava.exception.EgnAlreadyExistsException;
import com.endava.exception.EmailAlreadyExistsException;
import com.endava.exception.UserAlreadyDeactivatedException;
import com.endava.exception.UserHaveAssignedBooksException;
import com.endava.model.Reason;
import com.endava.model.Status;
import com.endava.model.User;
import com.endava.model.dto.UpdateUserDto;
import com.endava.model.dto.external.UserAssignment;
import com.endava.repository.UserRepository;
import com.endava.service.UserService;
import com.endava.specification.SpecificationParams;
import com.endava.specification.UserSpecification;
import com.endava.util.Constant;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@EnableScheduling
@Slf4j
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final AssignmentClient assignmentClient;

    @Autowired
    private UserRepository userRepository;

    private Validator validator;

    public UserServiceImpl(ModelMapper modelMapper, AssignmentClient assignmentClient, Validator validator) {
        this.modelMapper = modelMapper;
        this.assignmentClient = assignmentClient;
        this.validator = validator;
    }

    @Override
    public Optional<User> findByUserEgn(String egn) {
        return this.userRepository.findByEgn(egn);
    }

    @Override
    public Optional<User> findByUserEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user) {
        Optional<User> optionalUserFindByEgn = userRepository.findByEgn(user.getEgn());
        if (optionalUserFindByEgn.isPresent()) {
            throw new EgnAlreadyExistsException();
        }
        Optional<User> optionalUserFindByEmail = userRepository.findByEmail(user.getEmail());
        if (optionalUserFindByEmail.isPresent()) {
            throw new EmailAlreadyExistsException();
        }
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(UpdateUserDto updateUserDto, User foundUser) {
        if (updateUserDto.getFirstName() != null) {
            foundUser.setFirstName(updateUserDto.getFirstName());
        }

        if (updateUserDto.getLastName() != null) {
            foundUser.setLastName(updateUserDto.getLastName());
        }

        if (updateUserDto.getEmail() != null && updateUserDto.getConfirmEmail() != null) {
            foundUser.setEmail(updateUserDto.getEmail());
        }

        if (updateUserDto.getFullAddress() != null) {
            foundUser.setFullAddress(updateUserDto.getFullAddress());
        }

        UpdateUserDto patchedUser = this.modelMapper.map(foundUser, UpdateUserDto.class);
        validatePatchedUser(patchedUser);

        return this.userRepository.save(foundUser);
    }

    @Override
    public String deactivateUser(String egn, User foundUser) {

        if (foundUser.getReason() != null) {
            throw new UserAlreadyDeactivatedException(foundUser.getReason());
        }

        if (assignmentClient.isUserHaveAssignments(foundUser.getId())) {
            throw new UserHaveAssignedBooksException();
        } else {
            foundUser.setStatus(Status.INACTIVE);
            foundUser.setReason(Reason.REQUESTED);
            foundUser.setDateOfDeactivation(LocalDate.now());
        }


        this.userRepository.save(foundUser);

        return String.format(Constant.SUCCESSFULLY_DEACTIVATED, foundUser.getEgn(), foundUser.getReason(), foundUser.getReason().getDescription());
    }

    //Cron expression represents how often the method will be called.
    @Scheduled(cron = "${cron-expression}")
    public void deactivateInactiveUsers() {
        userRepository.findAll().stream().
                filter(user -> user.getStatus().equals(Status.ACTIVE))
                .forEach(user -> {
                    if (user.getLastLogIn() != null && user.getLastLogIn().isBefore(LocalDate.now().minusYears(1))) {
                        if (assignmentClient.isUserHaveAssignments(user.getId())) {
                            log.warn(String.format(Constant.USER_WAS_NOT_DEACTIVATED_SUCCESSFULLY_FOR_INACTIVITY, user.getId()));
                        } else {
                            user.setStatus(Status.INACTIVE);
                            user.setReason(Reason.INACTIVITY);
                            user.setDateOfDeactivation(LocalDate.now());
                            userRepository.save(user);
                            log.info(String.format(Constant.USER_WAS_DEACTIVATED_SUCCESSFULLY_FOR_INACTIVITY, user.getId()));
                        }
                    }
                });
    }

    @Override
    public List<UserAssignment> getSpecificationUsers(SpecificationParams specificationParams,
                                                      List<Long> usersId) {
        List<UserAssignment> specificationUsers = new ArrayList<>();
        Specification<User> searchSpecification = UserSpecification.getSearchSpecification(specificationParams);
        List<User> allUsersBySpecification = userRepository.findAll(searchSpecification);
        for (User user : allUsersBySpecification) {
            if (usersId.contains(user.getId())) {
                specificationUsers.add(mapToUserAssignment(user));
            }
        }
        return specificationUsers;
    }

    private UserAssignment mapToUserAssignment(User user) {
        UserAssignment userAssignment = new UserAssignment();
        userAssignment.setFullName(user.getFirstName() + " " + user.getLastName());
        userAssignment.setEmail(user.getEmail());
        userAssignment.setId(user.getId());
        return userAssignment;
    }

    private void validatePatchedUser(UpdateUserDto updateUserDto) {
        Set<ConstraintViolation<UpdateUserDto>> violations = validator.validate(updateUserDto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}