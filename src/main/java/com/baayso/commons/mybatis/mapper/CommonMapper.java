package com.baayso.commons.mybatis.mapper;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * 自定义通用Mapper。
 * <p>
 * 基于：MyBatis通用Mapper3（https://github.com/abel533/Mapper/tree/3.5.x）
 *
 * @author ChenFangjie (2016/4/4 18:45)
 * @since 1.0.0
 */
public interface CommonMapper<T> extends Mapper<T>, InsertListMapper<T>, IdsMapper<T> {

}
