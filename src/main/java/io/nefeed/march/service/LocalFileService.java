package io.nefeed.march.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 本地文件工具
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-12 17:06
 */
@Service
public class LocalFileService {

    private static Logger LOG = LoggerFactory.getLogger(LocalFileService.class);

    @Value(value="classpath:default.json")
    private Resource resource;

    public String readLocalContent() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        StringBuilder message = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            message.append(line);
        }
        String defaultString = message.toString();
        String result = defaultString.replace("\r\n", "").replaceAll(" +", "");
        LOG.debug("本地文件读取结果：{}", result);
        return result;
    }
}
