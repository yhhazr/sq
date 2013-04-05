/**
 * Copyright  2013-3-29 第七大道-技术支持部-网站开发组
 * 自主运营平台WEB 下午5:11:47
 * 版本号： v1.0
*/

package com.sz7road.web.common.util;

import java.util.Random;

/**
 * 类描述：生成新服推荐的验证码的工具类
 * 创建者： xin.fang
 * 项目名称： game_7road
 * 创建时间： 2013-3-29 下午5:11:47
 * 版本号： v1.0
*/
public class GeneVerificationCodeUtil {
    
    public static String  geneVerificationCode(){
        StringBuilder verificationCode = new StringBuilder();
        char[] codeSequence = { '0', '1', '2', '3', '4',
                '5', '6', '7', '8', '9' };
        Random random = new Random();
        
        for (int i = 0; i < 6; i++) {
            String rand = String.valueOf(codeSequence[random.nextInt(10)]).toUpperCase();
            verificationCode.append(rand);
        }
        return verificationCode.toString();
    }
}
