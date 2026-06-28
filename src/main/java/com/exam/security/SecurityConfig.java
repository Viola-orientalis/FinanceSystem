package com.exam.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // csrf 비활성화 ( 실무에서는 활성화 권장 )
        //http.csrf(csrf -> csrf.disable());
        http.csrf(AbstractHttpConfigurer::disable);

        //어떤 요청을 인증/비인증해야 되는지 설정
        http.authorizeHttpRequests(authorize ->
                        authorize
                                // /home은 로그인 후 메인화면이므로 permitAll에서 제외
                                .requestMatchers(
                                        "/",
                                        "/signup",
                                        "/login",
                                        "/css/**",
                                        "/error",
                                        "/js/**",
                                        "/images/**"
                                ).permitAll()
//                                .requestMatchers("/","/home","/signup","/login").hasRole("USER").permitAll()
                                .anyRequest().authenticated()
        );

        // 인증하기 위한 로그인 화면 구성하기
        http.formLogin(form ->
                form
                        // login화면을 보여주는 url 명시
                        // @GetMapping("/login")
                        .loginPage("/login")
                        // loginForm.html에서 사용하는 action값, name값 설정
                        /*
                            <form  th:action="@{/auth}" method="post">
                                아이디:<input type="text" name="userid"><br>
                                비밀번호:<input type="text" name="password"><br>
                              <input type="submit" value="로그인">
                            </form>
                         */
                        .loginProcessingUrl("/auth")
                        .usernameParameter("userid")
                        .passwordParameter("password")
                        //.successForwardUrl("/home")  // forward 요청
                        // true로 지정하면 이전에 가고자하는 경로가 아닌 /home으로 무조건 감.
                        .defaultSuccessUrl("/home", true) //redirect
                        //failureForwardUrl()  // forward 요청
                        // error=true 파라미터는 LoginForm.html에서 사용됨
                        .failureUrl("/login?error=true")
                        .permitAll());
        // 거의 대부분의 요청을 redirect로 처리함( PRG 패턴 )

        // 로그아웃 설정
        http.logout(logout ->
                logout
                        // logout를 처리할 떄 사용하는 url경로지정
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true) // session.invalidate() 동ㅇㄹ
                        .deleteCookies("JSESSIONID")  // 클라이언트에 남아있는 쿠키삭제
                        .permitAll());


        return http.build(); // 빌더패턴
    }

    // 암호화할 때 사용하는 빈
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}