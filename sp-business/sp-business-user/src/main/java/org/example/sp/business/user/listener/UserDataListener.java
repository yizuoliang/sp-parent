package org.example.sp.business.user.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import org.example.sp.common.entity.User;
import org.example.sp.business.user.service.IUserService;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yizl
 * @Date: 2020/5/22
 * @Description:
 */
@Slf4j
public class UserDataListener extends AnalysisEventListener<User> {
    /**
     * 每隔200条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 200;
    List<User> list = new ArrayList<User>();
    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private IUserService userService;

    public UserDataListener(IUserService userService) {
        this.userService=userService;
    }



    @Override
    public void invoke(User user, AnalysisContext context) {
        log.info("解析到一条数据:{}", user);
        list.add(user);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        userService.saveBatch(list);
        log.info("存储数据库成功！");
    }
}
