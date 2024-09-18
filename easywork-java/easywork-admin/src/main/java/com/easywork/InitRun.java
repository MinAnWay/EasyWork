package com.easywork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component("initRun")
public class InitRun implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(InitRun.class);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        //佛祖保佑  永不宕机    永无BUG
        String fozu =
                "                   _ooOoo_"+"\n"+
                        "                  o8888888o"+"\n"+
                        "                  88\" . \"88"+"\n"+
                        "                  (| -_- |)"+"\n"+
                        "                  O\\  =  /O"+"\n"+
                        "               ____/`---'\\____"+"\n"+
                        "             .'  \\\\|     |//  `."+"\n"+
                        "            /  \\\\|||  :  |||//  \\"+"\n"+
                        "           /  _||||| -:- |||||-  \\"+"\n"+
                        "           |   | \\\\\\  -  /// |   |"+"\n"+
                        "           | \\_|  ''\\---/''  |   |"+"\n"+
                        "           \\  .-\\__  `-`  ___/-. /"+"\n"+
                        "         ___`. .'  /--.--\\  `. . __"+"\n"+
                        "      .\"\" '<  `.___\\_<|>_/___.'  >'\"\"."+"\n"+
                        "     | | :  `- \\`.;`\\ _ /`;.`/ - ` : | |"+"\n"+
                        "     \\  \\ `-.   \\_ __\\ /__ _/   .-` /  /"+"\n"+
                        "======`-.____`-.___\\_____/___.-`____.-'======"+"\n"+
                        "                   `=---='"+"\n"+
                        "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+"\n"+
                        "   佛祖保佑            永不宕机           永无BUG";
        System.out.println(fozu);
        logger.info("管理端后台启动成功！！！");
    }
}
