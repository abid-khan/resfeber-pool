package com.resfeber.pool.service.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseBean {
    public Long id;
    public String uuid;
    public String status;
}
