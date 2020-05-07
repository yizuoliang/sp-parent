package org.example.sp.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yizl
 */
@Data
public class User implements Serializable {

    @TableId(type = IdType.UUID)
    private String id;

    private String name;

    private Integer age;

    private String email;

}
