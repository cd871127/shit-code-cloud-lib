package com.shit.code.cloud.mybatis.web;

import com.shit.code.cloud.mybatis.entity.BaseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Anthony
 * @date 11/1/20
 **/
public interface BaseCurdController<T extends BaseEntity> {

    BaseCurdService<T> getService();

    @PostMapping("")
    default T save(@RequestBody T t) {
        return getService().save(t);
    }

    @DeleteMapping("{id}")
    default T deleteById(@PathVariable("id") Long id) {
        return getService().deleteById(id);
    }

    @GetMapping("{id}")
    default T findById(@PathVariable("id") Long id) {
        return getService().findById(id);
    }

    @PutMapping("{id}")
    default T updateById(@PathVariable("id") Long id, @RequestBody T t) {
        if (t.getId() == null) {
            t.setId(id);
        } else if (!t.getId().equals(id)) {
            throw new IllegalArgumentException(String.format("路径参数Id：%s和请求body参数Id：%s不一致",
                    id, t.getId()));
        }
        return getService().updateById(t);
    }

}
