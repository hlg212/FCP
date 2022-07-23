package io.github.hlg212.task.xxl.model;

import lombok.Data;

import java.util.List;

/**
 * @author huangligui
 * @since 2022/7/23 13:30
 */
@Data
public class XxlPageResult<T> {

    private List<T> data;
    private Integer recordsTotal;

}
