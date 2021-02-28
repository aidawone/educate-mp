package he.edu.cms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import he.edu.cms.entity.CrmBanner;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author aidawone
 * @since 2021-02-27
 */
public interface CrmBannerService extends IService<CrmBanner> {

    List<CrmBanner> getListByIndex();
}
