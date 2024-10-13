package com.shoppingCart.Shopping_cart.secirity.config;

import com.shoppingCart.Shopping_cart.secirity.jwt.AuthTokenFilter;
import com.shoppingCart.Shopping_cart.secirity.jwt.JwtAuthEntryPoint;
import com.shoppingCart.Shopping_cart.secirity.user.ShopUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class ShopConfig {
    private final ShopUserDetailsService userDetailsService;
    private final JwtAuthEntryPoint authEntryPoint;

    //các URL được bảo mật
    private static final List<String> SECURED_URLS =
            List.of("/api/v1/carts/**", "/api/v1/cartItems/**");


    //Tạo một ModelMapper instance được dùng để map đối tượng giữa DTO và entity.
    //Không liên quan trực tiếp đến bảo mật nhưng có thể được sử dụng ở các phần khác của ứng dụng.
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    //BCryptPasswordEncoder băm mật khẩu an toàn để lưu trữ mật khẩu trong cơ sở dữ liệu
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //Khởi tạo bean AuthTokenFilter, một bộ lọc tùy chỉnh để chặn và xác minh token JWT từ các yêu cầu đến.
    //Bộ lọc này sẽ được thêm vào chuỗi bộ lọc trong filterChain().
    @Bean
    public AuthTokenFilter authTokenFilter() {
        return new AuthTokenFilter();
    }


    //quản lý các yêu cầu xác thực.
    //Thành phần này cần thiết để xác thực người dùng trong AuthTokenFilter
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    //xác thực người dùng từ cơ sở dữ liệu.
    //Sử dụng userDetailsService để truy xuất thông tin người dùng và passwordEncoder để xác minh mật khẩu.
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    //Phương thức này cấu hình các thiết lập bảo mật
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) //Vô hiệu hóa CSRF
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authEntryPoint)) //Xử lý ngoại lệ
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //Quản lý phiên
                .authorizeHttpRequests(auth -> auth.requestMatchers(SECURED_URLS.toArray(String[]::new)).authenticated()
                        .anyRequest().permitAll()); //Ủy quyền
        http.authenticationProvider(daoAuthenticationProvider()); //Provider xác thực
        http.addFilterBefore(authTokenFilter(), UsernamePasswordAuthenticationFilter.class); //Bộ lọc xác thực JWT
        return http.build();
    }
}
