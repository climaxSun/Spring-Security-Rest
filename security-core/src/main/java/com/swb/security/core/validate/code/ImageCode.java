package com.swb.security.core.validate.code;

import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.core.img.GraphicsUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author swb
 * 时间  2020-04-05 16:30
 * 文件  ImageCode
 */

public class ImageCode extends AbstractCaptcha {

    @Setter
    @Getter
    private LocalDateTime expireTime;

    private static final long serialVersionUID = 3180820918087507384L;

    /**
     * 构造
     *
     * @param width  图片宽
     * @param height 图片高
     */
    public ImageCode(int width, int height) {
        this(width, height, 5);
    }

    /**
     * 构造
     *
     * @param width     图片宽
     * @param height    图片高
     * @param codeCount 字符个数
     */
    public ImageCode(int width, int height, int codeCount) {
        this(width, height, codeCount, 15, 300);
    }

    /**
     * 构造
     *
     * @param width          图片宽
     * @param height         图片高
     * @param codeCount      字符个数
     * @param interfereCount 验证码干扰元素个数
     * @param expireIn       过期秒数
     */
    public ImageCode(int width, int height, int codeCount, int interfereCount, int expireIn) {
        super(width, height, codeCount, interfereCount);
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    @Override
    public Image createImage(String code) {
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D g = ImgUtil.createGraphics(image, ObjectUtil.defaultIfNull(this.background, Color.WHITE));

        // 随机画干扰圈圈
        drawInterfere(g);

        // 画字符串
        drawString(g, code);

        return image;
    }

    // ----------------------------------------------------------------------------------------------------- Private method start

    /**
     * 绘制字符串
     *
     * @param g    {@link Graphics}画笔
     * @param code 验证码
     */
    private void drawString(Graphics2D g, String code) {
        // 指定透明度
        if (null != this.textAlpha) {
            g.setComposite(this.textAlpha);
        }
        GraphicsUtil.drawStringColourful(g, code, this.font, this.width, this.height);
    }

    /**
     * 画随机干扰
     *
     * @param g {@link Graphics2D}
     */
    private void drawInterfere(Graphics2D g) {
        final ThreadLocalRandom random = RandomUtil.getRandom();

        for (int i = 0; i < this.interfereCount; i++) {
            g.setColor(ImgUtil.randomColor(random));
            g.drawOval(random.nextInt(width), random.nextInt(height), random.nextInt(height >> 1), random.nextInt(height >> 1));
        }
    }

    public boolean isExpried() {
        return LocalDateTime.now().compareTo(expireTime) >= 0;
    }

}
