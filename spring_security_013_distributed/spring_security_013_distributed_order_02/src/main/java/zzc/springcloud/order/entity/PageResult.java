package zzc.springcloud.order.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 分页结果类
 */
@Getter
@Setter
public class PageResult<T> {
    private long total;
    private int page;
    private int pageSize;
    private long maxPage;
    private List<T> rows;

    public PageResult() {}

    public PageResult(long total, int page, int pageSize, List<T> rows) {
        super();
        this.total = total;
        this.page = page;
        this.pageSize = pageSize;
        this.rows = rows;
    }
}