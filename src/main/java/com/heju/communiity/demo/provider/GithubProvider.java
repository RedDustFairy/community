package com.heju.communiity.demo.provider;

import com.alibaba.fastjson.JSON;
import com.heju.communiity.demo.dto.AccessTokenDTO;
import com.heju.communiity.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
//        RequestBody requestBody = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
//            Request request = new Request.Builder()
//                    .url("https://github.com/login/oauth/access_token")
//                    .post(requestBody)
//                    .build();
//            try (Response response = okHttpClient.newCall(request).execute()) {
//                String string = response.body().string();
//                System.out.println(string);
//                return string;
//            }
        return accessTokenDTO.getClient_secret();
        }
    public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            System.out.println(githubUser);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
