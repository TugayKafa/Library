package com.endava.client.user;

import com.endava.dto.external.ExternalUserDto;
import com.endava.dto.user.UserDetails;
import com.endava.dto.user.UserDto;
import com.endava.specification.UserSpecificationParams;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "userClient", url = "${user-api.url}")
public interface UserClient {

    @PostMapping(value = "/users/internal")
    ExternalUserDto getUserByEgn(@RequestBody UserDto userDto);

    @GetMapping(value = "/users/internal/usersId")
    List<UserDetails> getUserSpecification(@RequestParam List<Long> usersId, @RequestBody UserSpecificationParams userSpecificationParams);
}
