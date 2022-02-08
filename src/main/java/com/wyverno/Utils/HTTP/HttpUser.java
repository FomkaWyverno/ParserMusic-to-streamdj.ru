package com.wyverno.Utils.HTTP;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUser {
    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final HttpPost httpPost;

    public HttpUser(String requestURL) {
        this.httpPost = new HttpPost(requestURL);
    }

    public String requestPOST(Map<String,String> form) throws IOException {
        final List<NameValuePair> parameters = new ArrayList<>();
        for (Map.Entry<String,String> pair: form.entrySet()) {
            parameters.add(new BasicNameValuePair(pair.getKey(), pair.getValue()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(parameters));

        return getResponse();
    }

    private String getResponse() throws IOException {
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            return EntityUtils.toString(response.getEntity());
        }
    }

    public void close() throws IOException {
        httpClient.close();
    }
}
