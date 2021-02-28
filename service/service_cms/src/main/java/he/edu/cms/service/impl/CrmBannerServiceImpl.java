package he.edu.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import he.edu.cms.entity.CrmBanner;
import he.edu.cms.mapper.CrmBannerMapper;
import he.edu.cms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    public List<CrmBanner> getListByIndex() {
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.eq("is_deleted", 0)
                .orderByDesc("sort")
                .last("limit 4");
        return this.list(wrapper);
    }
}
