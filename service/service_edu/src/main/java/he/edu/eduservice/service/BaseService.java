package he.edu.eduservice.service;

import he.edu.commonutils.entity.ResultEntity;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 16:45 2021/2/7
 */
public interface BaseService {

    ResultEntity status(String id, Integer status);
}
