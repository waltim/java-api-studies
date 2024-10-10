package br.com.waltim.api.services;

import br.com.waltim.api.domain.vo.AccountCredentialsVO;
import br.com.waltim.api.domain.vo.TokenVO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<TokenVO> signin(AccountCredentialsVO data);
    ResponseEntity<TokenVO> refreshToken(String username, String refreshToken);
}
