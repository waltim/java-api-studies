package br.com.waltim.api.services;

import br.com.waltim.api.domain.Users;

public interface UserService {
    Users findById(Long id);
}
