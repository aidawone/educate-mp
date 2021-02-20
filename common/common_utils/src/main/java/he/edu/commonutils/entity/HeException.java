package he.edu.commonutils.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: aidawone
 * @Description:
 * @Date: Create in 11:30 2021/2/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeException extends RuntimeException {

    private Integer code;
    private String msg;
}
