package br.com.waltim.api.security.jwt;

import br.com.waltim.api.domain.vo.TokenVO;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ServletRequestAttributes servletRequestAttributes;

    @Mock
    private UserDetailsService userDetailsService;

    private HttpServletRequest request;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
    }

    @Test
    void createAccessToken() {
        when(servletRequestAttributes.getRequest()).thenReturn(request);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        jwtTokenProvider.init();
        String username = "testuser";
        List<String> roles = List.of("ROLE_USER");
        TokenVO tokenVO = jwtTokenProvider.createAccessToken(username, roles);

        assertNotNull(tokenVO);
        assertNotNull(tokenVO.getAccessToken());
        assertNotNull(tokenVO.getRefreshToken());
        assertEquals(username, tokenVO.getUsername());
    }

    @Test
    void refreshToken() {
        when(servletRequestAttributes.getRequest()).thenReturn(request);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        jwtTokenProvider.init();
        String username = "testuser";
        List<String> roles = List.of("ROLE_USER");
        TokenVO tokenVO = jwtTokenProvider.createAccessToken(username, roles);

        TokenVO newTokenVO = jwtTokenProvider.refreshToken(tokenVO.getRefreshToken());
        assertNotNull(newTokenVO);
        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());
        assertEquals(username, newTokenVO.getUsername());
    }

    @Test
    void getAuthentication() {
        when(servletRequestAttributes.getRequest()).thenReturn(request);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        jwtTokenProvider.init();
        String username = "testuser";
        List<String> roles = List.of("ROLE_USER");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("testuser");

        // Usando thenAnswer para configurar a resposta das authorities
        when(userDetails.getAuthorities()).thenAnswer(invocation -> List.of(new SimpleGrantedAuthority("ROLE_USER")));

        TokenVO tokenVO = jwtTokenProvider.createAccessToken(username, roles);

        String token = tokenVO.getAccessToken();
        Authentication authentication = jwtTokenProvider.getAuthentication(token);


        assertNotNull(authentication);
        assertEquals(username, authentication.getName());
        assertTrue(authentication.isAuthenticated());


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        assertNotNull(authorities);
        assertEquals(roles.size(), authorities.size());
        assertTrue(authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
    }


    @Test
    void resolveToken() {
        when(request.getHeader("Authorization")).thenReturn("Bearer testToken");
        String token = jwtTokenProvider.resolveToken(request);
        assertNotNull(token);
        assertEquals("testToken", token);
    }

    @Test
    void validateToken() {
        when(servletRequestAttributes.getRequest()).thenReturn(request);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
        jwtTokenProvider.init();
        String username = "testuser";
        List<String> roles = List.of("ROLE_USER");
        TokenVO tokenVO = jwtTokenProvider.createAccessToken(username, roles);

        boolean isValid = jwtTokenProvider.validateToken(tokenVO.getAccessToken());

        assertTrue(isValid);
    }
}