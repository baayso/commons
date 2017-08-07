package com.baayso.commons.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * 自定义Mybatis处理枚举转换类。
 *
 * @author ChenFangjie (2017/8/7 14:15)
 * @see org.apache.ibatis.type.EnumOrdinalTypeHandler
 * @since 1.0.0
 */
public class EnumValueTypeHandler<E extends Enum<E> & ValueEnum> extends BaseTypeHandler<E> {

    private       Class<E> type;
    private final E[]      enums;

    public EnumValueTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }

        this.type = type;
        this.enums = type.getEnumConstants();

        if (this.enums == null) {
            throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int i = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        }
        else {
            return this.valueOf(i);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int i = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        }
        else {
            return this.valueOf(i);
        }
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int i = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        }
        else {
            return this.valueOf(i);
        }
    }

    /* 枚举类型转换 */
    private E valueOf(int value) {
        for (E e : this.enums) {
            if (e.getValue() == value)
                return e;
        }

        throw new IllegalArgumentException("Cannot convert " + value + " to " + this.type.getSimpleName() + " by custom integer value.");
    }

}