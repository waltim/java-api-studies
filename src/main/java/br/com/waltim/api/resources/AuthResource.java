package br.com.waltim.api.resources;

import br.com.waltim.api.domain.vo.AccountCredentialsVO;
import br.com.waltim.api.domain.vo.TokenVO;
import br.com.waltim.api.services.AuthService;
import br.com.waltim.api.services.exceptions.InvalidJwtAuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthResource {

	private final AuthService authService;

	private AuthResource (AuthService authService){
		this.authService = authService;
	}

	@Operation(summary = "Authenticates a user and returns a token")
	@PostMapping(value = "/signin")
	public ResponseEntity<TokenVO> signin(@RequestBody AccountCredentialsVO data) {
		if (checkIfParamsIsNotNull(data))
			throw new InvalidJwtAuthenticationException("Invalid client request!");
		var token = authService.signin(data);
		if (token == null) throw new InvalidJwtAuthenticationException("Invalid client request!");
		return token;
	}

	@Operation(summary = "Refresh token for authenticated user and returns a token")
	@PutMapping(value = "/refresh/{username}")
	public ResponseEntity<TokenVO> refreshToken(@PathVariable("username") String username,
												@RequestHeader("Authorization") String refreshToken) {
		if (checkIfParamsIsNotNull(username, refreshToken))
			throw new InvalidJwtAuthenticationException("Invalid client request!");
		var token = authService.refreshToken(username, refreshToken);
		if (token == null) throw new InvalidJwtAuthenticationException("Invalid client request!");
		return token;
	}

	private boolean checkIfParamsIsNotNull(String username, String refreshToken) {
		return refreshToken == null || refreshToken.isBlank() ||
				username == null || username.isBlank();
	}

	private boolean checkIfParamsIsNotNull(AccountCredentialsVO data) {
		return data == null || data.getUsername() == null || data.getUsername().isBlank()
				 || data.getPassword() == null || data.getPassword().isBlank();
	}
}
