package not_unit;

import com.xuren.game.common.config.BaseConfig;
import com.xuren.game.common.redis.LettuceRedis;

/**
 * @author xuren
 */
public class TestRedis {
    public static void main(String[] args) {
        BaseConfig.getInstance().setSec("1");
        LettuceRedis.init("127.0.0.1", 6379, "", "");
        LettuceRedis.sync("1").set("name", "xuren");
    }
}
