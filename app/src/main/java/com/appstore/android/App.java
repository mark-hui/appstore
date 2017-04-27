package com.appstore.android;


import java.util.Arrays;

public class App {

    private String name;
    private int[] baiduPic = {R.drawable.baidu, R.drawable.baidu_pic1, R.drawable.baidu_pic2,
            R.drawable.baidu_pic3, R.drawable.baidu_pic4, R.drawable.baidu_pic5};
    private int[] iqiyiPic = {R.drawable.iqiyi, R.drawable.iqiyi_pic1, R.drawable.iqiyi_pic2,
            R.drawable.iqiyi_pic3, R.drawable.iqiyi_pic4, R.drawable.iqiyi_pic5};
    private int[] qqPic = {R.drawable.qq, R.drawable.qq_pic1, R.drawable.qq_pic2,
            R.drawable.qq_pic3, R.drawable.qq_pic4};
    private int[] taobaoPic = {R.drawable.taobao, R.drawable.taobao_pic1, R.drawable.taobao_pic2,
            R.drawable.taobao_pic3, R.drawable.taobao_pic4};
    private int[] ucPic = {R.drawable.uc, R.drawable.uc_pic1, R.drawable.uc_pic2,
            R.drawable.uc_pic3, R.drawable.uc_pic4, R.drawable.uc_pic5};
    private int[] wechatPic = {R.drawable.wechat, R.drawable.wechat_pic1, R.drawable.wechat_pic2,
            R.drawable.wechat_pic3};
    private int[] weiboPic = {R.drawable.weibo, R.drawable.weibo_pic1, R.drawable.weibo_pic2,
            R.drawable.weibo_pic3, R.drawable.weibo_pic4, R.drawable.weibo_pic5};
    private int[] wzryPic = {R.drawable.wzry, R.drawable.wzry_pic1, R.drawable.wzry_pic2,
            R.drawable.wzry_pic3, R.drawable.wzry_pic4, R.drawable.wzry_pic5};
    private int[] xxlPic = {R.drawable.xxl, R.drawable.xxl_pic1, R.drawable.xxl_pic2,
            R.drawable.xxl_pic3, R.drawable.xxl_pic4};
    private int[] youkuPic = {R.drawable.youku, R.drawable.youku_pic1, R.drawable.youku_pic2,
            R.drawable.youku_pic3, R.drawable.youku_pic4, R.drawable.youku_pic5};

    public App(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAppName() {
        switch (name) {
            case "baidu":
                return "百度";
            case "iqiyi":
                return "爱奇艺";
            case "qq":
                return "QQ";
            case "taobao":
                return "淘宝";
            case "uc":
                return "UC浏览器";
            case "wechat":
                return "微信";
            case "weibo":
                return "微博";
            case "wzry":
                return "王者荣耀";
            case "xxl":
                return "开心消消乐";
            case "youku":
                return "优酷";
            default:
        }
        return null;
    }

    public int getAppIcon() {
        switch (name) {
            case "baidu":
                return baiduPic[0];
            case "iqiyi":
                return iqiyiPic[0];
            case "qq":
                return qqPic[0];
            case "taobao":
                return taobaoPic[0];
            case "uc":
                return ucPic[0];
            case "wechat":
                return wechatPic[0];
            case "weibo":
                return weiboPic[0];
            case "wzry":
                return wzryPic[0];
            case "xxl":
                return xxlPic[0];
            case "youku":
                return youkuPic[0];
            default:
        }
        return 0;
    }

    public int[] getAppPic() {
        switch (name) {
            case "baidu":
                return Arrays.copyOfRange(baiduPic, 1, baiduPic.length);
            case "iqiyi":
                return Arrays.copyOfRange(iqiyiPic, 1, iqiyiPic.length);
            case "qq":
                return Arrays.copyOfRange(qqPic, 1, qqPic.length);
            case "taobao":
                return Arrays.copyOfRange(taobaoPic, 1, taobaoPic.length);
            case "uc":
                return Arrays.copyOfRange(ucPic, 1, ucPic.length);
            case "wechat":
                return Arrays.copyOfRange(wechatPic, 1, wechatPic.length);
            case "weibo":
                return Arrays.copyOfRange(weiboPic, 1, weiboPic.length);
            case "wzry":
                return Arrays.copyOfRange(wzryPic, 1, wzryPic.length);
            case "xxl":
                return Arrays.copyOfRange(xxlPic, 1, xxlPic.length);
            case "youku":
                return Arrays.copyOfRange(youkuPic, 1, youkuPic.length);
            default:
        }
        return new int[]{};
    }

    public int getAppText() {
        switch (name) {
            case "baidu":
                return R.string.baidu_text;
            case "iqiyi":
                return R.string.iqiyi_text;
            case "qq":
                return R.string.qq_text;
            case "taobao":
                return R.string.taobao_text;
            case "uc":
                return R.string.uc_text;
            case "wechat":
                return R.string.wechat_text;
            case "weibo":
                return R.string.weibo_text;
            case "wzry":
                return R.string.wzry_text;
            case "xxl":
                return R.string.xxl_text;
            case "youku":
                return R.string.youku_text;
            default:
        }
        return 0;
    }

    public int getAppUrl() {
        switch (name) {
            case "baidu":
                return R.string.baidu_url;
            case "iqiyi":
                return R.string.iqiyi_url;
            case "qq":
                return R.string.qq_url;
            case "taobao":
                return R.string.taobao_url;
            case "uc":
                return R.string.uc_url;
            case "wechat":
                return R.string.wechat_url;
            case "weibo":
                return R.string.weibo_url;
            case "wzry":
                return R.string.wzry_url;
            case "xxl":
                return R.string.xxl_url;
            case "youku":
                return R.string.youku_url;
            default:
        }
        return 0;
    }

}
