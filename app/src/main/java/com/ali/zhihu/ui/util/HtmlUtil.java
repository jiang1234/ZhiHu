package com.ali.zhihu.ui.util;

import com.ali.zhihu.bean.Article;
import com.ali.zhihu.bean.ReadTheme;

import java.util.List;

/**
 * Created by Administrator on 2018/2/4.
 */

public class HtmlUtil {
    //css样式 隐藏header
    private static final String HIDE_HEADER = "<style>div.headline{display:none;}</style>";
    private static final String NEEDED_FORMAT_CSS_TAG = "<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\"/>";

    // js script tag,需要格式化
    private static final String NEEDED_FORMAT_JS_TAG = "<script src=\"%s\"></script>";

    public static final String MIME_TYPE = "text/html; charset=utf-8";
    public static final String ENCODING = "utf-8";


    /**
     * 根据css链接生成Link标签
     *
     * @param url String
     * @return String
     */
    public static String createCssTag(String url) {
        return String.format(NEEDED_FORMAT_CSS_TAG, url);
    }

    /**
     * 根据多个css链接生成Link标签
     *
     * @param urls List<String>
     * @return String
     */
    public static String createCssTag(List<String> urls) {
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createCssTag(url));
        }
        return sb.toString();
    }

    /**
     * 根据js链接生成Script标签
     *
     * @param url String
     * @return String
     */
    public static String createJsTag(String url) {
        return String.format(NEEDED_FORMAT_JS_TAG, url);
    }

    /**
     * 根据多个js链接生成Script标签
     *
     * @param urls List<String>
     * @return String
     */
    public static String createJsTag(List<String> urls) {
        final StringBuilder sb = new StringBuilder();
        for (String url : urls) {
            sb.append(createJsTag(url));
        }
        return sb.toString();
    }
    private static String createHtmlData(String html,String css,String js){
        return css.concat(HIDE_HEADER).concat(html).concat(js);
    }
    public static String createHtmlData(Article article){
        return createHtmlData(article.getBody(),createCssTag(article.getCss()),createJsTag(article.getJs()));
    }

    public static String createHtmlData(ReadTheme article){
        return createHtmlData(article.getBody(),createCssTag(article.getCss()),createJsTag(article.getJs()));
    }
}
