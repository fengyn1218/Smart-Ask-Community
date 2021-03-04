package tk.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author fengyunan <fengyunan@kuaishou.com>
 * Created on 2021-03-04
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
