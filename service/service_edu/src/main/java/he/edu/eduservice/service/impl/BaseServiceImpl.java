package he.edu.eduservice.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import he.edu.commonutils.entity.ResultEntity;
import he.edu.eduservice.entity.Model;
import he.edu.eduservice.service.BaseService;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.google.gson.internal.$Gson$Types.getRawType;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 16:45 2021/2/7
 */
@Service
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService {


    @Override
    public ResultEntity status(String id, Integer status) {
        boolean flag = false;
        if (status.equals(1)) {
            flag = this.removeById(id);
        } else {
            try {
                Type superClass = getClass().getGenericSuperclass();
                Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
                Class<?> clazz = getRawType(type);
                Model model = (Model) clazz.newInstance();
//                Model model new T().getClass().newInstance();
//                t.getClass()
//                flag = this.updateById(t);
                
            } catch (InstantiationException e) {
                System.out.println("newInstance error" + e.getMessage());
            } catch (IllegalAccessException e) {
                System.out.println("IllegalAccess Error" + e.getMessage());
            }
        }

        if (flag) {
            return ResultEntity.ok();
        }
        return ResultEntity.error().message("delete error");
    }
}
