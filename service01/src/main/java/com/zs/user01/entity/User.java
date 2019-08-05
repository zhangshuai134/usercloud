package com.zs.user01.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseRowModel {
    @ExcelProperty(value = "id", index = 0)
    private int id;
    @ExcelProperty(value = "name", index = 1)
    private String name;
    @ExcelProperty(value = "age", index = 2)
    private int age;
}
