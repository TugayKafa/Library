package com.endava.controller;

import com.endava.exception.EmailAlreadyInUseException;
import com.endava.exception.EmailsNotMatchException;
import com.endava.exception.EmptyEgnException;
import com.endava.exception.UserNotFoundException;
import com.endava.model.User;
import com.endava.model.dto.DeactivateUserDto;
import com.endava.model.dto.ExternalUserDto;
import com.endava.model.dto.UpdateUserDto;
import com.endava.model.dto.UserDto;
import com.endava.model.dto.UserEgnDto;
import com.endava.model.dto.ViewUserDto;
import com.endava.model.dto.external.UserAssignment;
import com.endava.repository.UserRepository;
import com.endava.service.UserService;
import com.endava.specification.SpecificationParams;
import com.endava.specification.UserSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(ModelMapper modelMapper, UserService userService, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto newUser) {
        User user = modelMapper.map(newUser, User.class);
        User createdUser = userService.createUser(user);
        UserDto mappedDto = modelMapper.map(createdUser, UserDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(mappedDto);
    }

    @PatchMapping()
    ResponseEntity<UserDto> updateUser(@RequestBody UpdateUserDto updateUserDto) {

        if (updateUserDto.getEgn() == null) {
            throw new EmptyEgnException();
        }

        User findUser = userService.findByUserEgn(updateUserDto.getEgn())
                .orElseThrow(UserNotFoundException::new);

        if (!Objects.equals(updateUserDto.getEmail(), updateUserDto.getConfirmEmail())) {
            throw new EmailsNotMatchException();
        }

        if (updateUserDto.getEmail() != null && userService.findByUserEmail(updateUserDto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException();
        }

        User updatedUser = userService.updateUser(updateUserDto, findUser);
        UserDto mappedDto = modelMapper.map(updatedUser, UserDto.class);

        return ResponseEntity.ok(mappedDto);
    }

    @PatchMapping("/deactivate")
    public String deactivateUser(@Valid @RequestBody DeactivateUserDto userDto) {

        User findUser = userService.findByUserEgn(userDto.getEgn())
                .orElseThrow(UserNotFoundException::new);

        return userService.deactivateUser(userDto.getEgn(), findUser);
    }

    @GetMapping
    public Page<ViewUserDto> findAll(SpecificationParams specificationParams, Pageable pageable) {
        Page<User> page = userRepository.findAll(UserSpecification.getSearchSpecification(specificationParams), pageable);
        return page.map(user -> modelMapper.map(user, ViewUserDto.class));
    }

    @PostMapping("/internal")
    public ExternalUserDto getUserIdByEgn(@RequestBody UserEgnDto userEgnDto) {
        User user = userService.findByUserEgn(userEgnDto.getEgn()).orElseThrow(UserNotFoundException::new);
        return modelMapper.map(user, ExternalUserDto.class);
    }

    @GetMapping("/internal/usersId")
    public List<UserAssignment> getSpecificationUsers(@RequestParam List<Long> usersId,
                                                      @RequestBody SpecificationParams specificationParams) {
        return userService.getSpecificationUsers(specificationParams, usersId);
    }
}
