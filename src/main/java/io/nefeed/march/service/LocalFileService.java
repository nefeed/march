package io.nefeed.march.service;

import com.alibaba.fastjson.JSON;
import io.nefeed.march.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地文件工具
 *
 * @author 章华隽
 * @mail nefeed@163.com
 * @time 2018-04-12 17:06
 */
@Service
public class LocalFileService {

    private static Logger LOG = LoggerFactory.getLogger(LocalFileService.class);

    @Value(value = "classpath:default.json")
    private Resource resource;

    public String read() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        StringBuilder message = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            message.append(line);
        }
        String defaultString = message.toString();
        String result = defaultString.replace("\r\n", "");
        LOG.debug("本地文件读取结果：{}", result);
        return result;
    }

    public void write(String content) throws IOException {
        File file = resource.getFile();
        byte bytes[] = content.getBytes();
        int length = bytes.length;
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bytes, 0, length);
        fos.write(bytes);
        fos.close();
    }

    public List<Person> readPersonList() {
        String content;
        try {
            content = read();
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("本地文件读取出错！");
            return new ArrayList<>();
        }
        if (content == null || content.isEmpty()) {
            LOG.error("本地人员名单还未配置");
            return new ArrayList<>();
        }
        List<Person> people;
        try {
            people = JSON.parseArray(content, Person.class);
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("本地人员名单解析为队列失败");
            return new ArrayList<>();
        }
        if (people == null) {
            LOG.error("本地人员名单为null");
            try {
                write(JSON.toJSONString(new ArrayList<Person>()));
            } catch (IOException e) {
                e.printStackTrace();
                LOG.error("写入文件失败");
            }
            return new ArrayList<>();
        }
        return people;
    }
}
