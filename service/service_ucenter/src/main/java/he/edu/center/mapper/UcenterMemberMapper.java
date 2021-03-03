package he.edu.center.mapper;

import he.edu.center.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author aidawone
 * @since 2021-02-28
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    Long countUcenterByDay(@Param("day") String day);
}
