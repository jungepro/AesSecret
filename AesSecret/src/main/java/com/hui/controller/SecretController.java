package com.hui.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.hui.util.CdAesUtil;
import com.hui.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class SecretController {


    @GetMapping("doJiaMi")
    public Result<?> doJiaMi(String key, String txt) {
        if (StrUtil.isBlank(txt)) {
            return Result.error("原文为空！");
        }
        if (StrUtil.isBlank(key)) {
            key = RandomUtil.randomString("qwertyuioplkjhgfdsazxcvbnm123456789!@#$%^&*()", 16);
        }
        try {
            String encrypt = CdAesUtil.Encrypt(txt, key);
            JSONObject object = new JSONObject();
            object.putOpt("key",key);
            object.putOpt("txt",encrypt);
            return Result.OK(object);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("加密失败!");
        }
    }



    @GetMapping("doJieMi")
    public Result<?> doJieMi(String key, String txt) {
        if (StrUtil.isBlank(txt)) {
            return Result.error("密文为空！");
        }

        if (StrUtil.isBlank(key)) {
            return Result.error("密钥为空！");
        }
        try {
            String encrypt = CdAesUtil.Decrypt(txt, key);
            return Result.OK(encrypt);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("解密失败!");
        }
    }
}
