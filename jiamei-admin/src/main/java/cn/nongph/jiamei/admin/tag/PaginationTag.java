package cn.nongph.jiamei.admin.tag;

import com.github.miemiedev.mybatis.paginator.domain.PageList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport {

    private PageList pageList;
    //true表示需要其他参数 false表示不需要其他参数
    private boolean postParam = false;

    @Override
    public int doStartTag() throws JspException {
        if (pageList.getPaginator() != null && pageList.getPaginator().getTotalPages() > 1) {
            int current = pageList.getPaginator().getPage();
            int begin = Math.max(1, current - pageList.getPaginator().getLimit() / 2);
            int end = Math.min(begin + (pageList.getPaginator().getLimit() - 1), pageList.getPaginator().getTotalPages());

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<div class=\"pagination pagination-right col-sm-12 text-right text-center-xs\"><ul class=\"pagination pagination-sm m-t-none m-b-none\">");
            if (pageList.getPaginator().isHasPrePage()) {
                if(postParam){
                    stringBuilder.append("<li><a href=\"javascript:resubmitSearch(")
                        .append(1) .append(")\">&lt;&lt;</a></li>")
                            .append("<li><a href=\"javascript:resubmitSearch(").append(current - 1).append(")\">&lt;</a></li>");
                }else {
                    stringBuilder.append("<li><a href=\"?page=1\">&lt;&lt;</a></li>")
                            .append("<li><a href=\"?page=").append(current - 1).append("\">&lt;</a></li>");
                }
            } else {
                stringBuilder.append("<li class=\"disabled\"><a href=\"#\">&lt;&lt;</a></li>")
                        .append("<li class=\"disabled\"><a href=\"#\">&lt;</a></li>");
            }

            for (; begin <= end; begin++) {
                if (begin == current) {
                    if(postParam){
                        stringBuilder.append("<li class=\"active\"><a href=\"javascript:resubmitSearch(")
                            .append(begin).append(")\">").append(begin).append("</a></li>");
                    }else {
                        stringBuilder.append("<li class=\"active\"><a href=\"?page=").append(begin).append("\">").append(begin).append("</a></li>");
                    }
                } else {
                    if(postParam){
                        stringBuilder.append("<li><a href=\"javascript:resubmitSearch(")
                                .append(begin).append(")\">").append(begin).append("</a></li>");
                    }else {
                        stringBuilder.append("<li><a href=\"?page=").append(begin).append("\">").append(begin).append("</a></li>");
                    }
                }
            }

            if (pageList.getPaginator().isHasNextPage()) {
                if(postParam){
                    stringBuilder.append("<li><a href=\"javascript:resubmitSearch(").append(current + 1).append(")\">&gt;</a></li><li><a href=\"javascript:resubmitSearch(")
                            .append(pageList.getPaginator().getTotalPages()).append(")\">&gt;&gt;</a></li>");
                }else {
                    stringBuilder.append("<li><a href=\"?page=").append(current + 1).append("\">&gt;</a></li><li><a href=\"?page=").append(pageList.getPaginator().getTotalPages()).append("\">&gt;&gt;</a></li>");
                }
            } else {
                stringBuilder.append("<li class=\"disabled\"><a href=\"#\">&gt;</a></li><li class=\"disabled\"><a href=\"#\">&gt;&gt;</a></li>");
            }
            stringBuilder.append("</ul></div>");
            try {
                JspWriter out = pageContext.getOut();
                out.write(stringBuilder.toString());
            } catch (IOException ignored) {
                ignored.printStackTrace();
            }
        }
        return SKIP_BODY;
    }

    public void setPageList(PageList pageList) {
        this.pageList = pageList;
    }

    public void setPostParam(boolean postParam) {
        this.postParam = postParam;
    }
}
