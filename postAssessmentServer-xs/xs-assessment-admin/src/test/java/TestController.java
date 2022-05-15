import com.beneway.admin.AdminApplication;
import com.beneway.common.system.service.sys_unit.SysUnitService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AdminApplication.class})
@Slf4j
public class TestController {

    @Autowired
    private SysUnitService sysUnitService;

    @Test
    public void test(){
        log.error("{}",sysUnitService.getComUnitInfo(1));
    }
}
