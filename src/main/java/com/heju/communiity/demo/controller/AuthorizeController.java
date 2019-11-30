package com.heju.communiity.demo.controller;

import com.heju.communiity.demo.dto.AccessTokenDTO;
import com.heju.communiity.demo.dto.GithubUser;
import com.heju.communiity.demo.mapper.UserMapper;
import com.heju.communiity.demo.model.User;
import com.heju.communiity.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                            HttpServletRequest request) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubuser = githubProvider.getUser(accessToken);
        System.out.println(githubuser.getName());
        //登陆成功,写cookie和session
        User user = new User();
        user.setToken(UUID.randomUUID().toString());
        user.setCountId(String.valueOf(githubuser.getId()));
        user.setGmtCreate(System.currentTimeMillis());
        user.setName(githubuser.getName());
        user.setGmtModified(user.getGmtCreate());
        userMapper.insert(user);
        request.getSession().setAttribute("user", githubuser);
        return "redirect:/";
    }
}
