package com.humber.khana_khazana.service;

import com.humber.khana_khazana.DTO.UserRegisteredDTO;
import com.humber.khana_khazana.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface DefaultUserService extends UserDetailsService {

    User save(UserRegisteredDTO userRegisteredDTO);

}
