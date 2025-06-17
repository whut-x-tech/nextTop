package top.liuqiao.nextTop.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import top.liuqiao.nextTop.model.entity.User;

/**
 * @author laowang
 * @description 针对表【t_user】的数据库操作Mapper
 * @createDate 2025-06-12 21:08:51
 * @Entity top.liuqiao.nextTop.model.entity.User
 */
public interface UserMapper {

    User selectByAccount(@Param("account") String account);

    int addUser(@Param("user") User user);

    @Select("select username, avatar_url, total_days, consecutive_days," +
            " last_check_in_time, check_in_history from t_user where  id = #{id}")
    User getUserVoById(@Param("id") Long id);
}




