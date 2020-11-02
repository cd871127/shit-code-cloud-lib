package com.shit.code.cloud.mybatis.web;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shit.code.cloud.common.exception.CriticalException;
import com.shit.code.cloud.common.exception.NormalException;
import com.shit.code.cloud.mybatis.entity.BaseEntity;

import java.util.List;

import static com.shit.code.cloud.common.exception.ShitCodeExceptionEnum.*;

/**
 * 增删查改的基类
 *
 * @author Anthony
 * @date 11/1/20
 **/
public interface BaseCurdService<T extends BaseEntity> {

    BaseMapper<T> getMapper();

    /**
     * 插入一条数据
     *
     * @param t
     * @return
     */
    default T save(T t) {
        int count = getMapper().insert(t);
        if (count != 1) {
            throw new CriticalException(ERR_INSERT_COUNT, 1, count);
        }
        return findById(t.getId());
    }

    /**
     * 删除数据
     *
     * @param id
     * @return
     */
    default T deleteById(final long id) {
        T t = findById(id);

        int count = getMapper().deleteById(id);
        if (count != 1) {
            throw new CriticalException(ERR_DELETE_COUNT, 1, count);
        }
        return t;
    }

    /**
     * 查询
     *
     * @param id
     * @return
     */
    default T findById(final long id) {
        T t = getMapper().selectById(id);
        if (t == null) {
            throw new NormalException(ERR_DATA_NOT_EXIST, id);
        }
        return t;
    }

    /**
     * 更新
     *
     * @param t
     * @return
     */
    default T updateById(T t) {
        int count = getMapper().updateById(t);
        if (count != 1) {
            throw new CriticalException(ERR_UPDATE_COUNT, 1, count);
        }
        return findById(t.getId());
    }

}
