package com.feng.community.helper;

import static com.feng.community.constant.RedisKey.SEND_MAIL_CODE;
import static com.feng.community.constant.ResultViewCode.SEND_MAIL_FAIL;
import static com.feng.community.constant.ResultViewCode.SEND_MAIL_SUCCESS;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.feng.community.dto.ResultView;

import jdk.internal.org.objectweb.asm.commons.TryCatchBlockSorter;

/**
 * @author fengyunan
 * Created on 2021-03-08
 */
@Component
public class MailHelper {

    // 验证码
    private String code;
    // 社区说明
    private static final String SITE_NAME = "Aries问答社区";
    // 邮箱来源
    private static final String FROM_EMAIL = "2318699921@qq.com";
    // 缓存验证码过期时间
    private static final long EXPIRED_TIME = 300;

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    @Autowired
    private RedisHelper redisHelper;


    public ResultView sendMail(String email) {
        code = String.valueOf(new Random().nextInt(899999) + 100000);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(new InternetAddress(FROM_EMAIL, SITE_NAME + "官方邮件平台", "UTF-8"));
            //邮件接收者
            helper.setTo(email);

            helper.setSubject("欢迎使用" + SITE_NAME + "，离完成就差一步了"); // 邮件主题
            helper.setText("亲爱的用户：您好！感谢您使用" + SITE_NAME + "服务。您正在进行邮箱验证，请在验证码输入框中输入此次验证码："
                    + "<font face=\"宋体, arial, sans-serif\" size=\"5\" color=\"#ff0000\">" + code + "</font>"
                    + "以完成验证。如非本人操作，请忽略此邮件，由此给您带来的不便请谅解！", true); // 邮件内容
            javaMailSender.send(mimeMessage);
            //加入缓存
            addCache(email, code);
            return ResultView.success(SEND_MAIL_SUCCESS.getCode(), "邮件发送成功，请注意查收");

        } catch (Exception e) {
            return ResultView.fail(SEND_MAIL_FAIL.getCode(), "邮件发送失败，请稍后再试");
        }
    }

    /**
     * 将验证码存入redis，以做校验
     */
    private void addCache(String email, String code) {
        redisHelper.set(SEND_MAIL_CODE.of(email), code, EXPIRED_TIME);
    }


}
